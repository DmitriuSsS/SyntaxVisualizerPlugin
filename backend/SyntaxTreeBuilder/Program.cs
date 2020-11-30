using System;
using System.IO;
using System.Text;
using System.Text.Json;
using SyntaxTreeBuilder.Builder;

namespace SyntaxTreeBuilder
{
    public static class Program
    {
        public static void Main(string[] args)
        {
            var filePath = args[0];
            var encoding = args.Length >= 2 ? Encoding.GetEncoding(args[1]) : null;
            Console.Write(GetJsonTree(filePath, encoding));
        }
        
        public static string GetJsonTree(string path, Encoding encoding = null)
        {
            encoding ??= Encoding.UTF8;
            var code = string.Empty;

            if (File.Exists(path))
            {
                code = File.ReadAllText(path, encoding);
            }

            var tree = SimpleSyntaxNode.FromSourceCode(code);
            return JsonSerializer.Serialize(tree);
        }
    }
}