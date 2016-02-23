# ESImageSearchinge

ES Version required 1.0.1
To get ES+ Lire working you need to install LIRE plugin for ES
The Image Plugin is an Content Based Image Retrieval Plugin for Elasticsearch using LIRE (Lucene Image Retrieval). It allows users to index images and search for similar images.

It adds an image field type and an image query

See http://demo.elasticsearch-image.com for a demo of the plugin

In order to install the plugin, simply run: bin/plugin -install com.github.kzwang/elasticsearch-image/1.2.0.



You need to cerate index with name images  in ES with following mapping
{
 "mappings" :{
    "image": {
        "properties": {
            "name": {
                "type": "string"
            },
            "path":{
            	"type":"string"
            },
            "image": {
                "type": "image",
                "feature": {
                    "CEDD": {
                        "hash": "BIT_SAMPLING"
                    },
                    "JCD": {
                        "hash": ["BIT_SAMPLING", "LSH"]
                    },
                    "FCTH": {}
                }
            }
        }
    }
}}

Edit env.properties file under resource folder to point to your ES/Hadoop
