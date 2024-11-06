/*
* Copyright (C) 2016 Alexander Verbruggen
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package be.nabu.libs.eai.module.channels.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import be.nabu.eai.developer.api.InterfaceLister;
import be.nabu.eai.developer.util.InterfaceDescriptionImpl;

public class ChannelInterfaceLister implements InterfaceLister {
	
	private static Collection<InterfaceDescription> descriptions = null;
	
	@Override
	public Collection<InterfaceDescription> getInterfaces() {
		if (descriptions == null) {
			synchronized(ChannelInterfaceLister.class) {
				if (descriptions == null) {
					List<InterfaceDescription> descriptions = new ArrayList<InterfaceDescription>();
					descriptions.add(new InterfaceDescriptionImpl("Channels", "Single Handler", "be.nabu.libs.channels.api.SingleChannelResultHandler.handle"));
					descriptions.add(new InterfaceDescriptionImpl("Channels", "Batch Handler", "be.nabu.libs.channels.api.ChannelResultHandler.handle"));
					descriptions.add(new InterfaceDescriptionImpl("Channels", "Recovery Selector", "be.nabu.libs.channels.api.ChannelRecoverySelector.recover"));
					descriptions.add(new InterfaceDescriptionImpl("Channels", "Channel In Provider", "be.nabu.libs.eai.module.channels.api.ServiceChannelProvider.transactIn"));
					descriptions.add(new InterfaceDescriptionImpl("Channels", "Channel Out Provider", "be.nabu.libs.eai.module.channels.api.ServiceChannelProvider.transactOut"));
					descriptions.add(new InterfaceDescriptionImpl("Channels", "Channel In/Out Provider", "be.nabu.libs.eai.module.channels.api.ServiceChannelProvider.transactInOut"));
					ChannelInterfaceLister.descriptions = descriptions;
				}
			}
		}
		return descriptions;
	}

}
