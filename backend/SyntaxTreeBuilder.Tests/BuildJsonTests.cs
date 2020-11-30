using System.IO;
using NUnit.Framework;

namespace SyntaxTreeBuilder.Tests
{
    [TestFixture]
    public class BuildJsonTests
    {
        // Придётся вставить свой путь, потому что не работает с ../../
        private const string FolderForTestFiles = 
            "C:/Users/Dmitriy/Desktop/SyntaxVisualizerPlugin/backend/SyntaxTreeBuilder.Tests/FilesForJsonTests/";
        
        [TestCase("code1.cs","json1.json")]
        [TestCase("code2.cs","json2.json")]
        public void CheckBuildJson(string codeFile, string jsonFile)
        {
            codeFile = FolderForTestFiles + codeFile;
            jsonFile = FolderForTestFiles + jsonFile;
            
            var json = File.ReadAllText(jsonFile);
            var actualJsonForCode = Program.GetJsonTree(codeFile);

            Assert.AreEqual(json, actualJsonForCode);
        }
    }
}