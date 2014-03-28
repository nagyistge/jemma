package org.energy_home.jemma.osgi.ah.zigbee.appliances;

import java.util.Dictionary;

import org.energy_home.jemma.ah.cluster.ah.ConfigServer;
import org.energy_home.jemma.ah.hac.ApplianceException;
import org.energy_home.jemma.ah.hac.IEndPointTypes;
import org.energy_home.jemma.ah.hac.ServiceClusterException;
import org.energy_home.jemma.ah.zigbee.zcl.cluster.general.ZclBasicClient;
import org.energy_home.jemma.ah.zigbee.zcl.cluster.general.ZclBasicServer;
import org.energy_home.jemma.ah.zigbee.zcl.cluster.general.ZclIdentifyServer;
import org.energy_home.jemma.ah.zigbee.zcl.cluster.general.ZclLevelControlClient;
import org.energy_home.jemma.ah.zigbee.zcl.cluster.general.ZclOnOffClient;
import org.energy_home.jemma.ah.zigbee.zcl.cluster.general.ZclPowerConfigurationServer;
import org.energy_home.jemma.ah.zigbee.zcl.cluster.security.ZclIASZoneServer;
import org.energy_home.jemma.ah.zigbee.zcl.lib.ZclAppliance;
import org.energy_home.jemma.ah.zigbee.zcl.lib.ZclEndPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZclBitronhomeMovementAppliance extends ZclAppliance{

private ZclEndPoint endPoint = null;
	
	private static final Logger LOG = LoggerFactory.getLogger(ZclBitronhomeMovementAppliance.class);

	
	public ZclBitronhomeMovementAppliance(String pid, Dictionary config)
			throws ApplianceException {
		super(pid, config);
		endPoint = this.zclAddEndPoint(IEndPointTypes.ZIGBEE_LEVEL_CONTROLLABLE_OUTPUT);
		
		// Bitronhome Movement sensor
		/**
		 * Endpoint:0x01
			|-------------------------------------------------|
			| Server Side     			|	    Client Side   |			
			|-------------------------------------------------|
			|			   			Mandatory    	          | 			
			| Basic(0x0000)	  			|			 	  	  |			
			| Identify(0x0003)			|					  |			 			
			| IAS Zone (0x0500)	        |					  |			
			|			    		Optional	              |			
			|None						|		None		  |
			|-------------------------------------------------|

		 */
		
		// Server Clusters
		endPoint.addServiceCluster(new ZclBasicServer()); 	 // adding Basic (0x0000)
		endPoint.addServiceCluster(new ZclIdentifyServer()); // adding Identify (0x0003)		
		endPoint.addServiceCluster(new ZclIASZoneServer()); 	 // adding IAS Zone(0x0500)		
		
		

		
		ConfigServer serviceCluster = (ConfigServer) this.getEndPoint(0).getServiceCluster("org.energy_home.jemma.ah.cluster.ah.ConfigServer");
		if (serviceCluster != null) {
			try {
				if (serviceCluster.getIconName(null) == null) {
					//serviceCluster.setIconName("lampadina.png", null);
				}
			} catch (ServiceClusterException e) {
				
			}
		}
	}

	protected void attached() {
		if (LOG.isDebugEnabled())	
			{ 
				LOG.debug("attached");
			}
	}

	protected void detached() {
		if(LOG.isDebugEnabled()){
			LOG.debug("detached");
		}
		
	}

}
