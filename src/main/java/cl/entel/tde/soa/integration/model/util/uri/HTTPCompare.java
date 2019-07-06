package cl.entel.tde.soa.integration.model.util.uri;

import cl.entel.tde.soa.integration.model.esb.Uri;

public class HTTPCompare implements TransportCompare {
    @Override
    public boolean equals(Uri source, Uri destination) {
        String sourcePath = source.getPath();
        String destinationPath = destination.getPath();
        return source.equals(destinationPath);
    }
}
