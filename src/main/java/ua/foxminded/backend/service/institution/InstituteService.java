package ua.foxminded.backend.service.institution;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.backend.exception.illegalstate.institution.FacultyAlreadyExistsException;
import ua.foxminded.backend.exception.notfound.institution.FacultyNotFoundException;
import ua.foxminded.backend.exception.notfound.institution.InstituteNotFoundException;
import ua.foxminded.backend.model.institution.Faculty;
import ua.foxminded.backend.model.institution.Institute;
import ua.foxminded.backend.repository.institution.InstituteRepository;
import ua.foxminded.backend.service.BaseService;

@Service
public class InstituteService extends BaseService<Institute, InstituteRepository, InstituteNotFoundException> {

    protected InstituteService(InstituteRepository instituteRepository) {
        super(instituteRepository);
    }

    @Transactional
    public Institute addFaculty(String instituteId, Faculty faculty) {
        Institute institute = getById(instituteId);

        if (facultyExistsInInstitution(institute, faculty.getId())) {
            throw new FacultyAlreadyExistsException("Faculty with id: " + faculty.getId() +
                                                    " already exists in this Institute with id: " + instituteId);
        }

        institute.getFaculties().add(faculty);

        return repository.save(institute);
    }

    @Transactional
    public Institute removeFacultyById(String instituteId, String facultyId) {
        Institute institute = getById(instituteId);

        Faculty facultyToRemove = institute.getFaculties().stream()
                .filter(faculty -> faculty.getId().equals(facultyId))
                .findAny()
                .orElseThrow(() -> new FacultyNotFoundException("Faculty with id: " + facultyId +
                                                                " not found in this Institution with id: " + instituteId));

        institute.getFaculties().remove(facultyToRemove);

        return repository.save(institute);
    }

    private boolean facultyExistsInInstitution(Institute institute, String facultyId) {
        return institute.getFaculties().stream()
                .anyMatch(faculty -> faculty.getId().equals(facultyId));
    }

    @Override
    protected InstituteNotFoundException createNotFoundException(String message) {
        return new InstituteNotFoundException(message);
    }
}
