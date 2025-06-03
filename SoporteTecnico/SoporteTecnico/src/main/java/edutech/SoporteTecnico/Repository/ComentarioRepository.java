package edutech.SoporteTecnico.Repository;

import edutech.SoporteTecnico.Model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
