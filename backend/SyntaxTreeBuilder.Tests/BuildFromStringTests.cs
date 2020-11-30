using System.Collections.Generic;
using FluentAssertions;
using Microsoft.CodeAnalysis.CSharp;
using NUnit.Framework;
using SyntaxTreeBuilder.Builder;

namespace SyntaxTreeBuilder.Tests
{
    public class BuildFromStringTests
    {
        [TestCase("")]
        [TestCase("using System;")]
        [TestCase("namespace A{}")]
        [TestCase("namespace A{class B{}}")]
        public void AllTries_StartWithCompilationUnit(string code)
        {
            var tree = SimpleSyntaxNode.FromSourceCode(code);
            
            Assert.AreEqual(SyntaxKind.CompilationUnit, tree.Kind);
            Assert.AreEqual(SyntaxNodeType.Node, tree.Type);
        }

        [Test]
        public void SimpleTest_When_EmptyCode()
        {
            const string code = "";
            var actualTree = SimpleSyntaxNode.FromSourceCode(code);
            
            var endOfFile = new SimpleSyntaxNode(
                SyntaxNodeType.Token, 
                SyntaxKind.EndOfFileToken,
                value: "");
            
            var expectedTree = new SimpleSyntaxNode(
                SyntaxNodeType.Node,
                SyntaxKind.CompilationUnit,
                new List<SimpleSyntaxNode> { endOfFile });

            expectedTree.Should().BeEquivalentTo(actualTree);
        }
    }
}