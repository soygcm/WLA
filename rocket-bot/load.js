const { OpenAIEmbeddings } = require("langchain/embeddings");
const { HNSWLib } = require("langchain/vectorstores");
// const { TextLoader } = require("langchain/document_loaders");
const { config } = require('dotenv');
config();

async function run() {

  try{
    // console.log("hello world", process.env.OPENAI_API_KEY)
    // const loader = new TextLoader("embeddings/agile.txt");
  
    // const docs = await loader.load();
    const model = new OpenAI({});

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
    const directory = "embeddings";
    // await vectorStore.save("embeddings");
  
    // Load the vector store from the same directory
    const loadedVectorStore = await HNSWLib.load(
      directory,
      new OpenAIEmbeddings()
    );
  
    const chain = RetrievalQAChain.fromLLM(model, loadedVectorStore.asRetriever());
    const res = await chain.call({
      query: "¿Que se ocupa para iniciar mi projecto?",
    });
    console.log({ res });
  
      // res.status(200).json({ result: {success: result} })
  }catch(error){
    console.error(error)
  }

  
}

run();