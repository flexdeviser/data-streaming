package org.e4s.reference.services;


import java.util.List;
import org.e4s.reference.dto.Device;
import org.e4s.reference.repos.DeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("deviceService")
public class DeviceService {

    @Autowired
    private DeviceRepo deviceRepo;

    public List<Device> devices() {
        return deviceRepo.findAll();
    }

    @Cacheable(value = "devices", key = "#name")
    public Device get(final String name) {
        return deviceRepo.findByName(name);
    }

    public Device add(final String name, final String type, final String description) {
        return deviceRepo.save(new Device(name, type, description));
    }


}
