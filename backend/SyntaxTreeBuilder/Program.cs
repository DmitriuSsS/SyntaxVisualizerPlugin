using System;
using System.IO;
using System.Text;
using System.Text.Json;
using SyntaxTreeBuilder.Builder;

namespace SyntaxTreeBuilder
{
    class Program
    {
        static void Main(string[] args)
        {
            var filePath = args[0];
            Console.Out.Write(GetJsonTree(new FileInfo(filePath)));
        }
        
        private static string GetJsonTree(FileInfo file, Encoding encoding = null)
        {
            encoding ??= Encoding.UTF8;
            var code = string.Empty;

            if (file.Exists)
            {
                using var stream = new StreamReader(file.OpenRead(), encoding);
                code = stream.ReadToEnd();
            }

            var tree = new MySyntaxTree(code);
            return JsonSerializer.Serialize(tree);
        }
    }
}