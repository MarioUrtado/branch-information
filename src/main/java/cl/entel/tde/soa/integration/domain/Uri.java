package cl.entel.tde.soa.integration.domain;

//import javax.persistence.*;

//@Entity(name = "Uri")
//@Table(name = "URI")
public class Uri {

    //@Id
    private String id;

    private String uri;

    private String host;

    private String port;

    private String path;

    private String protocol;

    private String cluster;

    public Uri() {
    }

    public Uri(String uri, String transport, String resourceType){
        super();
        try {
            if (resourceType.equals(ResourceBusType.PROXY_SERVICE)){
                this.uri = uri;
                this.protocol = transport;
                this.cluster="";
                this.host="";
                this.port="";
                this.path = uri;

            }
            if (resourceType.equals(ResourceBusType.BUSINESS_SERVICE)){
                this.uri = uri;
                this.protocol = uri.split(":")[0];
                if (uri.contains("://")) {
                    this.cluster = uri.split("://")[1].split("/")[0];
                } else if (uri.contains(":/")) {
                    this.cluster = uri.split(":/")[1].split("/")[0];
                } else {
                    this.cluster = "";
                }
                if (!this.cluster.contains(",")) {
                    this.host = this.cluster.split(":")[0];
                    if (this.cluster.contains(":")) {
                        this.port = this.cluster.split(":")[1];
                    } else {
                        this.port = "";
                    }
                    this.host = "";
                }
                if (uri.split(this.cluster).length > 1) {
                    this.path = uri.split(this.cluster)[1];
                } else {
                    this.path = "";
                }
            }


        } catch (Exception e ){
            System.out.println(e.getMessage());
        }


    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
