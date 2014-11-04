package edu.ucla.mii.edu.ucla.mii.neo4j;

/**
 * Created by matiasz on 2014-11-03.
 */
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.GraphDatabaseAPI;
import org.neo4j.server.WrappingNeoServerBootstrapper;
import org.neo4j.server.configuration.Configurator;
import org.neo4j.server.configuration.ServerConfigurator;
import org.neo4j.shell.ShellSettings;

public class Server {

    public static void main(String args[]){
        {
            String currentDirectory = System.getProperty("user.dir");
            String databasePath = currentDirectory + "/neo4j-community-2.1.5/data/graph.db";
            GraphDatabaseAPI graphDb = (GraphDatabaseAPI) new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder(databasePath).
                setConfig(GraphDatabaseSettings.node_keys_indexable, "name").
                setConfig(GraphDatabaseSettings.relationship_keys_indexable, "name").
                setConfig(GraphDatabaseSettings.node_auto_indexing, "true").
                setConfig(GraphDatabaseSettings.relationship_auto_indexing, "true").
                newGraphDatabase();
            ServerConfigurator config;
            config = new ServerConfigurator(graphDb);
            // let the server endpoint be on a custom port
            config.configuration().setProperty(Configurator.WEBSERVER_PORT_PROPERTY_KEY, 7575);
            WrappingNeoServerBootstrapper server;
            server = new WrappingNeoServerBootstrapper( graphDb, config );
            server.start();
        }
    }
}