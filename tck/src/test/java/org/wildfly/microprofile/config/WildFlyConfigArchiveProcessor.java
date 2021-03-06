/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.microprofile.config;

import org.wildfly.microprofile.config.inject.ConfigExtension;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author <a href="http://jmesnil.net/">Jeff Mesnil</a> (c) 2017 Red Hat inc.
 */
public class WildFlyConfigArchiveProcessor  implements ApplicationArchiveProcessor {

    @Override
    public void process(Archive<?> applicationArchive, TestClass testClass) {
        if (applicationArchive instanceof WebArchive) {
            JavaArchive configJar = ShrinkWrap
                    .create(JavaArchive.class, "wildlfy-config-impl.jar")
                    .addPackage(WildFlyConfig.class.getPackage())
                    .addPackage(ConfigExtension.class.getPackage())
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                    .addAsServiceProvider(ConfigProviderResolver.class, WildFlyConfigProviderResolver.class);

            ((WebArchive) applicationArchive).addAsLibraries(configJar);
        }
    }
}