package techcourse.myblog.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.myblog.application.dto.UserDto;
import techcourse.myblog.domain.User;
import techcourse.myblog.domain.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public String save(UserDto userDto) {
        return userRepository.save(new User(userDto.getEmail(), userDto.getName(), userDto.getPassword())).getEmail();
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        List<UserDto> userDtos = new ArrayList<>();
        userRepository.findAll().forEach(user -> userDtos.add(new UserDto(user)));

        return userDtos;
    }
}
