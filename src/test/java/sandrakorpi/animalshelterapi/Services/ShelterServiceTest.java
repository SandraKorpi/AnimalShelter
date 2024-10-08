package sandrakorpi.animalshelterapi.Services;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sandrakorpi.animalshelterapi.Dtos.ShelterDto;
import sandrakorpi.animalshelterapi.Models.Shelter;
import sandrakorpi.animalshelterapi.Repositories.ShelterRepository;
import static org.mockito.Mockito.when;


class ShelterServiceTest {

    @Mock
    private ShelterRepository shelterRepository;

    @InjectMocks
    private ShelterService shelterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ShelterDto shelterDto = new ShelterDto(1L, "Test Shelter", "Test Address", "123456789", "Hej@Test.com", "10");
        Shelter shelter = new Shelter();
            shelter.setId(shelterDto.getId());
            shelter.setName(shelterDto.getName());
            shelter.setAddress(shelterDto.getAddress());
            shelter.setPhone(shelterDto.getPhone());
            shelter.setEmail(shelterDto.getEmail());
            shelter.setAvailableBeds(shelterDto.getAvailableBeds());
    }

    @Test
    public void testCreateNewShelter() {
        // Arrange
        long id = 1;
        ShelterDto shelterDto = new ShelterDto(id, "Test Shelter", "Test Address", "123456789", "Hej@Test.com", "10");

        // Skapa en Shelter-instans
        Shelter shelter = new Shelter();
        shelter.setId(id); // Sätt id
        shelter.setName(shelterDto.getName());
        shelter.setAddress(shelterDto.getAddress());
        shelter.setPhone(shelterDto.getPhone());
        shelter.setEmail(shelterDto.getEmail());
        shelter.setAvailableBeds(shelterDto.getAvailableBeds());

        // Mocka repository-funktionen
        when(shelterRepository.save(any(Shelter.class))).thenReturn(shelter); // Använda 'Shelter' som typ

        // Act
        ShelterDto createdShelter = shelterService.createNewShelter(shelterDto);

        // Assert
        assertNotNull(createdShelter);
        assertEquals(shelterDto.getName(), createdShelter.getName());
        assertEquals(shelterDto.getAddress(), createdShelter.getAddress());
        assertEquals(shelterDto.getPhone(), createdShelter.getPhone());
        assertEquals(shelterDto.getEmail(), createdShelter.getEmail());
        assertEquals(shelterDto.getAvailableBeds(), createdShelter.getAvailableBeds());

        // Verifiera att save-metoden kallades exakt en gång
        verify(shelterRepository, times(1)).save(any(Shelter.class));
    }



}