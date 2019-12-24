/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package info.revenberg.song.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import info.revenberg.song.business.entities.SeedStarter;
import info.revenberg.song.business.entities.repositories.SeedStarterRepository;
import info.revenberg.song.dao.jpa.BundleRepository;
import info.revenberg.song.domain.Bundle;

@Service
public class SeedStarterService {

    @Autowired
    private BundleRepository bundleRepository;

    @Autowired
    private SeedStarterRepository seedstarterRepository;

    public SeedStarterService() {
        super();
    }

    public List<Bundle> findAll() {
        return this.bundleRepository.findAll();
  //      return this.seedstarterRepository.findAll();
    }

    public void add(final SeedStarter seedStarter) {
        this.seedstarterRepository.add(seedStarter);
    }
    
}
