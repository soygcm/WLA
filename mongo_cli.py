import pymongo
import click
import json

@click.command()
@click.option('--host', default='localhost', help='MongoDB host')
@click.option('--port', default=27017, help='MongoDB port')
@click.option('--database', required=True, help='MongoDB database name')
@click.option('--collection', required=True, help='MongoDB collection name')
@click.option('--query', required=True, help='MongoDB query')
def mongo_cli(host, port, database, collection, query):
    client = pymongo.MongoClient(host, port)
    db = client[database]
    collection = db[collection]
    query = json.loads(query)
    result = collection.find(query)
    result_list = list(result)
    if len(result_list) == 0:
        print("Vacio")
    for document in result:
        print(document)
    client.close()

if __name__ == '__main__':
    mongo_cli()
