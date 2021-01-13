using System.Collections.Generic;
using System.Linq;
using FluentAssertions;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.Text;
using NUnit.Framework;
using SyntaxTreeBuilder.Builder;

namespace SyntaxTreeBuilder.Tests
{
    [TestFixture]
    public class BuildFromSyntaxTests
    {
        private SyntaxNode _roslynTree;

        [SetUp]
        public void SetUp()
        { 
            const string code = @"using System;
// comment
public class C {
    public void M() {
        Console.Beep();
    }
}";
            _roslynTree = CSharpSyntaxTree.ParseText(code).GetRoot();
        }

        [Test]
        public void CheckBuild_ForNode()
        {
            CheckBuild_ForNode(
                _roslynTree, 
                new [] {SyntaxKind.UsingDirective, SyntaxKind.ClassDeclaration, SyntaxKind.EndOfFileToken});
            
            CheckBuild_ForNode(
                _roslynTree.FindNode(new TextSpan(0, 13)),
                new [] {SyntaxKind.UsingKeyword, SyntaxKind.IdentifierName, SyntaxKind.SemicolonToken});
            
            CheckBuild_ForNode(
                _roslynTree.FindNode(new TextSpan(25, 70)),
                new [] {SyntaxKind.PublicKeyword, SyntaxKind.ClassKeyword, SyntaxKind.IdentifierToken,
                    SyntaxKind.OpenBraceToken, SyntaxKind.MethodDeclaration, SyntaxKind.CloseBraceToken});
        }
        
        private static void CheckBuild_ForNode(SyntaxNode node, IEnumerable<SyntaxKind> childrenOrder)
        {
            var tree = SimpleSyntaxNode.FromSyntax(node);
            
            tree.Type.Should().BeEquivalentTo(SyntaxNodeType.Node);
            tree.Kind.Should().BeEquivalentTo(node.Kind());
            tree.Value.Should().BeEmpty();
            tree.Children
                .Select(c => c.Kind)
                .Should()
                .BeEquivalentTo(childrenOrder);
        }

        [Test]
        public void CheckBuild_ForToken()
        {
            CheckBuild_ForToken(
                _roslynTree.FindToken(25),
                new [] {SyntaxKind.SingleLineCommentTrivia, SyntaxKind.EndOfLineTrivia, 
                    SyntaxKind.None, SyntaxKind.WhitespaceTrivia});
            
            CheckBuild_ForToken(
                _roslynTree.FindToken(38),
                new [] {SyntaxKind.None, SyntaxKind.WhitespaceTrivia});
            
            CheckBuild_ForToken(
                _roslynTree.FindToken(101),
                new SyntaxKind[0]);
        }

        private static void CheckBuild_ForToken(SyntaxToken token, IReadOnlyCollection<SyntaxKind> childrenOrder)
        {
            var tree = SimpleSyntaxNode.FromSyntax(token);
            
            tree.Type.Should().BeEquivalentTo(SyntaxNodeType.Token);
            tree.Kind.Should().BeEquivalentTo(token.Kind());

            if (!childrenOrder.Any())
            {
                tree.Value.Should().BeEquivalentTo(token.ValueText);
                tree.Children.Should().BeEmpty();
            }
            else
            {
                tree.Value.Should().BeEmpty();
                tree.Children
                    .Select(c => c.Kind)
                    .Should()
                    .BeEquivalentTo(childrenOrder);
            }
        }

        [Test]
        public void CheckBuild_ForTrivia()
        {
            CheckBuild_ForTrivia(_roslynTree.FindTrivia(13));
        }
        
        private static void CheckBuild_ForTrivia(SyntaxTrivia trivia)
        {
            var tree = SimpleSyntaxNode.FromSyntax(trivia);
            
            tree.Type.Should().BeEquivalentTo(SyntaxNodeType.Trivia);
            tree.Kind.Should().BeEquivalentTo(trivia.Kind());
            tree.Value.Should().BeEquivalentTo(trivia.ToFullString());
            tree.Children.Should().BeEmpty();
        }

        [Test]
        public void CheckBuild_ForValue()
        {
            new List<string> { "qwe", "rty", "uio", "p[]" }
                .ForEach(CheckBuild_ForValue);
        }
        
        private static void CheckBuild_ForValue(string value)
        {
            var tree = SimpleSyntaxNode.FromSyntax(value);

            tree.Type.Should().BeEquivalentTo(SyntaxNodeType.Value);
            tree.Kind.Should().BeEquivalentTo(SyntaxKind.None);
            tree.Value.Should().BeEquivalentTo(value);
            tree.Children.Should().BeEmpty();
        }
    }
}