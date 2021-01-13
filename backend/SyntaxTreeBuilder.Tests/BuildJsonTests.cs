using System.IO;
using NUnit.Framework;

namespace SyntaxTreeBuilder.Tests
{
    [TestFixture]
    public class BuildJsonTests
    {
        private readonly string _folderForTestFiles = 
            Path.Combine("..", "..", "..", "FilesForJsonTests");

        [TestCase("code1.txt","json1.json")]
        [TestCase("code2.txt","json2.json")]
        public void CheckBuildJson(string codeFile, string jsonFile)
        {
            codeFile = Path.Combine(_folderForTestFiles, codeFile);
            jsonFile = Path.Combine(_folderForTestFiles, jsonFile);
            
            var json = File.ReadAllText(jsonFile);
            var code = File.ReadAllText(codeFile);
            
            var actualJsonForCodeFile = Program.GetJsonTreeFromFile(codeFile);
            var actualJsonForCode = Program.GetJsonTreeFromCode(code);

            Assert.AreEqual(json, actualJsonForCodeFile);
            Assert.AreEqual(json, actualJsonForCode);
        }
    }
}