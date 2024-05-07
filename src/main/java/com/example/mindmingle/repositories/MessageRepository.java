package com.example.mindmingle.repositories;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {
    List<Message> findByGroupe_IdGroupe(int groupId);


}
