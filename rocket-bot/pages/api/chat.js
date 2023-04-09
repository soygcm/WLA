import { OpenAI } from "langchain";
import { HNSWLib } from "langchain/vectorstores";
import { OpenAIEmbeddings } from "langchain/embeddings";
import { RetrievalQAChain } from "langchain/chains";
import path from 'path';


export default async function (req, res) {

  // const stream = new ReadableStream({

  // })

  const model = new OpenAI({ openAIApiKey: process.env.OPENAI_API_KEY });

  const embeddings = path.join(process.cwd(), "embeddings")

  const loadedVectorStore = await HNSWLib.load(
    embeddings,
    new OpenAIEmbeddings({ openAIApiKey: process.env.OPENAI_API_KEY })
  );

  // const result = await loadedVectorStore.similaritySearch(req.body.question, 1);

  const chain = RetrievalQAChain.fromLLM(model, loadedVectorStore.asRetriever());
  // console.log(chain)
  const result = await chain.call({
    query: req.body.question,
  });
  console.log({ result });


  res.status(200).json({ result: { success: result.text } })
}