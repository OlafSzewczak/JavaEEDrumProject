package pl.edu.ug.oszewczak.javaeedrumproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.ug.oszewczak.javaeedrumproject.domain.Shell;

@Repository
public interface ShellRepository extends CrudRepository<Shell, Long> {
}
