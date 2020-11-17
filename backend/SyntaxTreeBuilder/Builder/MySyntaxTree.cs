using System.Collections.Generic;
using System.Linq;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;
using Microsoft.CodeAnalysis.Text;

namespace SyntaxTreeBuilder.Builder
{
public class MySyntaxTree
    {
        public string NodeType { get; }
        public string Kind { get; }
        public string Value { get; } = string.Empty;
        private TextSpan Span { get; }
        public IReadOnlyList<MySyntaxTree> Children { get; }

        public MySyntaxTree(string code) : this(GetCompilationUnit(code))
        {
        }

        private MySyntaxTree(SyntaxNode node)
        {
            NodeType = nameof(SyntaxNodeType.Node);
            Kind = node.Kind().ToString();
            Span = node.FullSpan;

            var children = new List<MySyntaxTree>();
            children.AddRange(node.ChildTokens().Select(childToken => new MySyntaxTree(childToken)));
            children.AddRange(node.ChildNodes().Select(childNode => new MySyntaxTree(childNode)));
            children.Sort((tree1, tree2) => tree1.Span.Start - tree2.Span.Start);
            Children = children;
        }

        private MySyntaxTree(SyntaxToken token)
        {
            NodeType = nameof(SyntaxNodeType.Token);
            Kind = token.Kind().ToString();
            Span = token.FullSpan;

            var trivia = token.GetAllTrivia().ToArray();
            if (trivia.Length == 0)
            {
                Value = token.ValueText;
                return;
            }

            var children = new List<MySyntaxTree>();
            children.AddRange(trivia.Select(childTrivia => new MySyntaxTree(childTrivia)));
            children.Add(new MySyntaxTree(token.ValueText, token.Span));
            children.Sort((tree1, tree2) => tree1.Span.Start - tree2.Span.Start);
            Children = children;
        }

        private MySyntaxTree(string value, TextSpan span)
        {
            NodeType = nameof(SyntaxNodeType.Value);
            Kind = string.Empty;
            Value = value;
            Span = span;
            Children = new List<MySyntaxTree>();
        }

        private MySyntaxTree(SyntaxTrivia trivia)
        {
            NodeType = nameof(SyntaxNodeType.Trivia);
            Kind = trivia.Kind().ToString();
            Span = trivia.FullSpan;
            Children = new List<MySyntaxTree>();
            Value = trivia.ToFullString();
        }

        private static CompilationUnitSyntax GetCompilationUnit(string code)
        {
            var tree = CSharpSyntaxTree.ParseText(code);
            return tree.GetCompilationUnitRoot();
        }
    }
}