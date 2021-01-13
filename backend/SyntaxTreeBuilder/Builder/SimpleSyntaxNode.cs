using System.Collections.Generic;
using System.Linq;
using System.Text.Json.Serialization;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;

namespace SyntaxTreeBuilder.Builder
{
    public class SimpleSyntaxNode
    {
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public SyntaxNodeType Type { get; }

        [JsonConverter(typeof(JsonStringEnumConverter))]
        public SyntaxKind Kind { get; }

        public string Value { get; }

        public IReadOnlyList<SimpleSyntaxNode> Children { get; }

        public SimpleSyntaxNode(SyntaxNodeType type,
            SyntaxKind kind,
            List<SimpleSyntaxNode> children = null,
            string value = null)
        {
            Type = type;
            Kind = kind;
            Value = value ?? string.Empty;
            Children = children ?? new List<SimpleSyntaxNode>();
        }

        public static SimpleSyntaxNode FromSourceCode(string code)
        {
            var root = CSharpSyntaxTree.ParseText(code).GetRoot();
            return FromSyntax(root);
        }

        public static SimpleSyntaxNode FromSyntax(SyntaxNode node)
        {
            var children = node
                .ChildNodesAndTokens()
                .Select(nodeOrToken => nodeOrToken.IsNode
                    ? FromSyntax(nodeOrToken.AsNode())
                    : FromSyntax(nodeOrToken.AsToken()))
                .ToList();

            return new SimpleSyntaxNode(SyntaxNodeType.Node, node.Kind(), children);
        }

        public static SimpleSyntaxNode FromSyntax(SyntaxToken token)
        {
            var children = token.LeadingTrivia.Select(FromSyntax).ToList();
            children.Add(FromSyntax(token.ValueText));
            children.AddRange(token.TrailingTrivia.Select(FromSyntax));

            return children.Count == 1 
                ? new SimpleSyntaxNode(SyntaxNodeType.Token, token.Kind(), value: token.ToFullString()) 
                : new SimpleSyntaxNode(SyntaxNodeType.Token, token.Kind(), children);
        }

        public static SimpleSyntaxNode FromSyntax(SyntaxTrivia trivia)
        {
            return new SimpleSyntaxNode(SyntaxNodeType.Trivia, trivia.Kind(), value: trivia.ToFullString());
        }

        public static SimpleSyntaxNode FromSyntax(string value)
        {
            return new SimpleSyntaxNode(SyntaxNodeType.Value, SyntaxKind.None, value: value);
        }
    }
}