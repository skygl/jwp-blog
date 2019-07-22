package techcourse.myblog.application.converter;

import techcourse.myblog.application.dto.UserDto;
import techcourse.myblog.domain.User;

public class UserConverter extends Converter<UserDto, User> {
    private static UserConverter converter = new UserConverter();

    private UserConverter() {
        super(userDto -> new User(userDto.getEmail(), userDto.getName(), userDto.getPassword())
                , user -> new UserDto(user.getEmail(), user.getName(), user.getPassword()));
    }

    public static UserConverter getInstance() {
        return converter;
    }
}