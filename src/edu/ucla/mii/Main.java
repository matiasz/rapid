package edu.ucla.mii;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.ReadableIndex;

public class Main
{
    private static final String DB_PATH = System.getProperty("user.dir") + "/neo4j-community-2.1.5/";

    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }

    public static enum RelationshipTypes implements RelationshipType
    {
        KNOWS
    }

    public static void main(String[] args)
    {
        String currentDirectory = System.getProperty("user.dir");
        String databasePath = currentDirectory + "/neo4j-community-2.1.5/data/graph.db";
        GraphDatabaseService graphDb = new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder(databasePath).
                setConfig(GraphDatabaseSettings.node_keys_indexable, "name").
                setConfig(GraphDatabaseSettings.relationship_keys_indexable, "name").
                setConfig(GraphDatabaseSettings.node_auto_indexing, "true").
                setConfig(GraphDatabaseSettings.relationship_auto_indexing, "true").
                newGraphDatabase();
        registerShutdownHook(graphDb);

//        Relationship relationship;
//        Node firstNode;
//        Node secondNode;
//        Transaction tx;
//
//        firstNode = graphDb.createNode();
//        firstNode.setProperty( "message", "Hello, " );
//        secondNode = graphDb.createNode();
//        secondNode.setProperty( "message", "World!" );
//
//        relationship = firstNode.createRelationshipTo( secondNode, RelationshipTypes.KNOWS );
//        relationship.setProperty( "message", "Hi, Neo4j." );

        ReadableIndex<Node> autoNodeIndex = graphDb.index()
                .getNodeAutoIndexer()
                .getAutoIndex();
        System.out.println(autoNodeIndex.getName());
        System.out.println("Done");
    }
}