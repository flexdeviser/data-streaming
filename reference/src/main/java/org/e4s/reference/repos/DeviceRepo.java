package org.e4s.reference.repos;

import java.util.UUID;
import org.e4s.reference.dto.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepo extends JpaRepository<Device, UUID> {

    Device findByName(String name);
}
