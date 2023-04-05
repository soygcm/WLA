import { OpenAIEmbeddings } from "langchain/embeddings";
import { HNSWLib } from "langchain/vectorstores";
import { TextLoader } from "langchain/document_loaders";
import { config } from 'dotenv'
config()

export default async function run() {

  try{
    // console.log("hello world", process.env.OPENAI_API_KEY)
    // const loader = new TextLoader("embeddings/pequeno.txt");
  
    // const docs = await loader.load();
  
    //   console.log("hello world")
    // // Create a vector store through any method, here from texts as an example
    // const vectorStore = await HNSWLib.fromDocuments(
    //   docs,
    //   new OpenAIEmbeddings({openAIApiKey:  process.env.OPENAI_API_KEY})
    // );
  
    // console.log("vectorStore", vectorStore)
  
    // Save the vector store to a directory
    const directory = "embeddings";
    // await vectorStore.save("embeddings");
  
    // Load the vector store from the same directory
    const loadedVectorStore = await HNSWLib.load(
      directory,
      new OpenAIEmbeddings()
    );
  
    // vectorStore and loadedVectorStore are identical
  
    const result = await loadedVectorStore.similaritySearch("nan", 1);
    console.log("result", result);
  
      // res.status(200).json({ result: {success: result} })
  }catch(error){
    console.error(error)
  }

  
}

run();