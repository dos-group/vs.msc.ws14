package de.tuberlin.cit.sdn.opendaylight.helium;

import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.data.DataProviderService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;

import java.util.List;

/**
 * taken from
 * http://sdntutorials.com/how-to-look-up-topology-and-links-in-md-sal/
 */
public class TopologyClient {
    private DataProviderService provider;

    public TopologyClient(BindingAwareBroker.ProviderContext session) {
        provider = session.getSALService(DataProviderService.class);
    }

    public List<Node> getNodes() {
        InstanceIdentifier<Node> instanceIdentifier =
                InstanceIdentifier.builder().node(Node.class).toInstance();
        return ((Nodes) provider.readOperationalData(instanceIdentifier)).getNode();
    }
}
