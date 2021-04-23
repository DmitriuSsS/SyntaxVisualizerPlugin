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
            switch (args[0])
            {
                case "-f":
                case "--file":
                    var filePath = args[1];
                    var encoding = args.Length > 2 ? Encoding.GetEncoding(args[2]) : null;
                    Console.Write(GetJsonTreeFromFile(filePath, encoding));
                    break;
              
                case "-c":
                case "--code":
                    var code = args[1];
                    Console.Write(GetJsonTreeFromCode(code));
                    break;
                
                default:
                    Console.Error.Write($"Unknown key: {args[0]}");
                    break;
            }
        }

        public static string GetJsonTreeFromFile(string path, Encoding encoding = null)
        {
            encoding ??= Encoding.UTF8;
            var code = string.Empty;

            if (File.Exists(path))
            {
                code = File.ReadAllText(path, encoding);
            }

            var tree = SimpleSyntaxNode.FromSourceCode(code);
            var options = new JsonSerializerOptions { MaxDepth = (int)1e4 };
            return JsonSerializer.Serialize(tree, typeof(SimpleSyntaxNode), options);
        }

        public static string GetJsonTreeFromCode(string code)
        {
            var tree = SimpleSyntaxNode.FromSourceCode(code);
            return JsonSerializer.Serialize(tree);
        }
    }
}