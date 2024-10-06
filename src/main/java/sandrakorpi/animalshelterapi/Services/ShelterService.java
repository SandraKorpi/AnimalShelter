package sandrakorpi.animalshelterapi.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sandrakorpi.animalshelterapi.Dtos.ShelterDto;
import sandrakorpi.animalshelterapi.Models.Shelter;
import sandrakorpi.animalshelterapi.Repositories.ShelterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShelterService {

    @Autowired
    private ShelterRepository shelterRepository;

    public List<Shelter> getAllShelters() {
        return shelterRepository.findAll();
    }

    public Optional<Shelter> getOneShelter(long id) {
        return shelterRepository.findById(id);
    }

    public Shelter createNewShelter(ShelterDto shelterDto) {
        Shelter shelter = new Shelter();
        shelter.setName(shelterDto.getName());
        shelter.setAddress(shelterDto.getAddress());
        shelter.setPhone(shelterDto.getPhone());
        shelter.setEmail(shelterDto.getEmail());
        shelter.setAvailableBeds(shelterDto.getAvailableBeds());
        return shelterRepository.save(shelter);
    }

    public Optional<Shelter> updateOneShelter(long id, ShelterDto shelterDto) {
        return shelterRepository.findById(id).map(existingShelter -> {
            existingShelter.setName(shelterDto.getName());
            existingShelter.setAddress(shelterDto.getAddress());
            existingShelter.setPhone(shelterDto.getPhone());
            existingShelter.setEmail(shelterDto.getEmail());
            existingShelter.setAvailableBeds(shelterDto.getAvailableBeds());
            return shelterRepository.save(existingShelter);
        });
    }

    public boolean updateAvailableBeds(long id, String availableBeds) {
        Optional<Shelter> optionalShelter = shelterRepository.findById(id);
        if (optionalShelter.isPresent()) {
            Shelter shelter = optionalShelter.get();
            shelter.setAvailableBeds(availableBeds);
            shelterRepository.save(shelter);
            return true;
        }
        return false;
    }

    public boolean deleteOneShelter(long id) {
        if (shelterRepository.existsById(id)) {
            shelterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Shelter> getShelterByName(String name) {
        return shelterRepository.findByName(name);
    }
}
