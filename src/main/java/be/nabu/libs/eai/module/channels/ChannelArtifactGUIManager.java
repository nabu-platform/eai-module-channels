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

package be.nabu.libs.eai.module.channels;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import be.nabu.eai.developer.MainController;
import be.nabu.eai.developer.managers.base.BaseJAXBGUIManager;
import be.nabu.eai.repository.resources.RepositoryEntry;
import be.nabu.libs.channels.api.ChannelProvider;
import be.nabu.libs.eai.module.channels.util.ChannelManagerImpl;
import be.nabu.libs.eai.module.channels.util.ChannelServiceProvider;
import be.nabu.libs.property.api.Property;
import be.nabu.libs.property.api.Value;
import be.nabu.libs.types.TypeUtils;
import be.nabu.libs.types.api.ComplexType;
import be.nabu.libs.types.api.Element;
import be.nabu.libs.types.java.BeanResolver;

public class ChannelArtifactGUIManager extends BaseJAXBGUIManager<ChannelConfiguration, ChannelArtifact> {

	public ChannelArtifactGUIManager() {
		super("Channel", ChannelArtifact.class, new ChannelArtifactManager(), ChannelConfiguration.class);
	}

	@Override
	protected List<Property<?>> getCreateProperties() {
		return null;
	}

	@Override
	protected ChannelArtifact newInstance(MainController controller, RepositoryEntry entry, Value<?>... values) throws IOException {
		return new ChannelArtifact(entry.getId(), entry.getContainer(), entry.getRepository());
	}
	@Override
	@SuppressWarnings("unchecked")
	public <V> void setValue(ChannelArtifact instance, Property<V> property, V value) {
		if ("providerId".equals(property.getName())) {
			Map<String, String> properties = getConfiguration(instance).getProperties();
			if (properties == null) {
				properties = new LinkedHashMap<String, String>();
			}
			else {
				properties.clear();
			}
			if (value != null) {
				ChannelProvider<?> channelProvider = ChannelManagerImpl.getInstance().getProviders().get(((String) value));
				ComplexType type = channelProvider instanceof ChannelServiceProvider ? ((ChannelServiceProvider) channelProvider).getPropertyType() : (ComplexType) BeanResolver.getInstance().resolve(channelProvider.getPropertyClass());
				for (Element<?> element : TypeUtils.getAllChildren(type)) {
					properties.put(element.getName(), properties.get(element.getName()));
				}
			}
			getConfiguration(instance).setProperties(properties);
		}
		if (!"properties".equals(property.getName())) {
			super.setValue(instance, property, value);
		}
		else if (value instanceof Map) {
			getConfiguration(instance).getProperties().putAll(((Map<? extends String, ? extends String>) value));
		}
	}
	
	@Override
	public String getCategory() {
		return "Frameworks";
	}
}
