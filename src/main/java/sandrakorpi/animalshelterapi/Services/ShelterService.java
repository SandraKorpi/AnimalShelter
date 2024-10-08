package sandrakorpi.animalshelterapi.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sandrakorpi.animalshelterapi.Dtos.ShelterDto;
import sandrakorpi.animalshelterapi.Models.Shelter;
import sandrakorpi.animalshelterapi.Repositories.ShelterRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShelterService {


    private ShelterRepository shelterRepository;


    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public List<Shelter> getAllShelters() {
        return shelterRepository.findAll();
    }

    public Optional<Shelter> getShelterByName(String name) {
        return shelterRepository.findByName(name);
    }

    public Optional<Shelter> getOneShelter(long id) {
        return shelterRepository.findById(id);
    }

    public ShelterDto createNewShelter(ShelterDto shelterDto) {
        Shelter shelter = convertToEntity(shelterDto);
        Shelter savedShelter = shelterRepository.save(shelter);
            return convertToDto(savedShelter);
    }

    public Optional<ShelterDto> updateOneShelter(long id, ShelterDto shelterDto) {
        return shelterRepository.findById(id)
                .map(existingShelter -> {
                    existingShelter.setName(shelterDto.getName());
                    existingShelter.setAddress(shelterDto.getAddress());
                    existingShelter.setPhone(shelterDto.getPhone());
                    existingShelter.setEmail(shelterDto.getEmail());
                    existingShelter.setAvailableBeds(shelterDto.getAvailableBeds());
                    Shelter updatedShelter = shelterRepository.save(existingShelter);
                        return convertToDto(updatedShelter);
                });
    }

    public boolean updateAvailableBeds(long id, String availableBeds) {
        return shelterRepository.findById(id)
                .map(shelter -> {
                    shelter.setAvailableBeds(availableBeds);
                    shelterRepository.save(shelter);
                        return true;
                })
                .orElse(false);
    }

    public boolean deleteOneShelter(long id) {
        if (shelterRepository.existsById(id)) {
            shelterRepository.deleteById(id);
                return true;
        }
        return false;
    }

    private ShelterDto convertToDto(Shelter shelter) {  //Ändrade om för att bättre matcha Mellbergs!
        ShelterDto shelterDto = new ShelterDto();
        shelterDto.setName(shelter.getName());
        shelterDto.setAddress(shelter.getAddress());
        shelterDto.setPhone(shelter.getPhone());
        shelterDto.setEmail(shelter.getEmail());
        shelterDto.setAvailableBeds(shelter.getAvailableBeds());
            return shelterDto;
    }

    private Shelter convertToEntity(ShelterDto shelterDto) {
        Shelter shelter = new Shelter();
        shelter.setName(shelterDto.getName());
        shelter.setAddress(shelterDto.getAddress());
        shelter.setPhone(shelterDto.getPhone());
        shelter.setEmail(shelterDto.getEmail());
        shelter.setAvailableBeds(shelterDto.getAvailableBeds());
            return shelter;
    }
}
