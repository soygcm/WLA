import { OpenAIEmbeddings } from "langchain/embeddings";
import { HNSWLib } from "langchain/vectorstores";
import { OpenAI } from "langchain/llms"
import { config } from 'dotenv';
import { RecursiveCharacterTextSplitter } from "langchain/text_splitter";
import { RetrievalQAChain } from "langchain/chains";
import globalTunnel from 'global-tunnel-ng'

import * as fs from "fs";
config();

async function load() {
  const text = fs.readFileSync("embeddings/agile.txt", "utf8");
    const textSplitter = new RecursiveCharacterTextSplitter({ chunkSize: 1000 });
    const docs = await textSplitter.createDocuments([text]);


  
      // console.log("hello world")
    // Create a vector store through any method, here from texts as an example
    const vectorStore = await HNSWLib.fromDocuments(
      docs,
      new OpenAIEmbeddings({openAIApiKey:  process.env.OPENAI_API_KEY})
    );
  
    console.log("vectorStore", vectorStore)
  
    // Save the vector store to a directory
    await vectorStore.save("embeddings");
}

async function run() {

  globalTunnel.initialize();

  try{
    // console.log("hello world", process.env.OPENAI_API_KEY)
    // const loader = new TextLoader("embeddings/agile.txt");
  
    // const docs = await loader.load();
    const model = new OpenAI({openAIApiKey:  process.env.OPENAI_API_KEY});
  
    // Load the vector store from the same directory
    const loadedVectorStore = await HNSWLib.load(
      "embeddings",
      new OpenAIEmbeddings()
    );
  
    const chain = RetrievalQAChain.fromLLM(model, loadedVectorStore.asRetriever());
    const res = await chain.call({
      query: "¿Que se ocupa para iniciar mi projecto?",
    });
    console.log({ res });

    // const result = await loadedVectorStore.similaritySearch("¿Que se ocupa para iniciar mi projecto?", 1);
    // console.log("result", result);
  
      // res.status(200).json({ result: {success: result} })
  }catch(error){
    console.error(error)
  }

  
}

run();