package FYP19.Service;

import FYP19.Dao.StudentsMapper;
import FYP19.Dao.TeacherMapper;
import FYP19.Entities.Students;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private StudentsMapper studentsMapper;
    private TeacherMapper teacherMapper;
    public void setStudentMapper(StudentsMapper studentMapper){
        this.studentsMapper = studentMapper;
    }

    public void setTeacherMapper(TeacherMapper teacherMapper){
        this.teacherMapper = teacherMapper;
    }

    public String BcryptStu(int ucd_id, String plainPwd){
        String salt = BCrypt.gensalt();
        studentsMapper.updateSaltById(ucd_id, salt);
        String cipherPwd = BCrypt.hashpw(plainPwd,salt);
        return cipherPwd;
    }

    public String BcryptTea(int teacher_id, String plainPwd){
        String salt = BCrypt.gensalt();
        teacherMapper.updateSaltById(teacher_id, salt);
        String cipherPwd = BCrypt.hashpw(plainPwd,salt);
        return cipherPwd;
    }


}
