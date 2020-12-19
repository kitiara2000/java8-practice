package edu.java8.practice.service.impl;

import edu.java8.practice.domain.Privilege;
import edu.java8.practice.domain.User;
import edu.java8.practice.service.UserService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    @Override
    public List<User> sortByAgeDescAndNameAsc(final List<User> users) {
        return users.stream()
                .sorted(Comparator.comparingInt(User::getAge).reversed())
                //              .sorted(Comparator.comparing(User::getFirstName)
                .collect(Collectors.toList());
    }

    @Override
    public List<Privilege> getAllDistinctPrivileges(final List<User> users) {
        return users.stream()
                .flatMap(s -> s.getPrivileges().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUpdateUserWithAgeHigherThan(final List<User> users, final int age) {
        users.stream().forEach(System.out::println);
        return users.stream()
                .filter(user -> user.getPrivileges().get(0).equals(Privilege.UPDATE))    //I`ve tried to filter Privileges as list too but unsuccessful(((
                .filter(user -> user.getAge() > age)
                .findFirst();
    }

    @Override
    public Map<Integer, List<User>> groupByCountOfPrivileges(final List<User> users) {
        return users.stream()
                .collect(Collectors.groupingBy(user -> user.getPrivileges().size(), Collectors.toList()));
    }

    @Override // both methods are working
    public double getAverageAgeForUsers(final List<User> users) {
        if (users.isEmpty()) return -1;
        //return users.stream().mapToDouble(User::getAge).summaryStatistics().getAverage();
        return users.stream().collect(Collectors.averagingDouble(User::getAge));
    }

    @Override
    public Optional<String> getMostFrequentLastName(final List<User> users) {
       return users.stream()
               .collect(Collectors.groupingBy(User::getLastName, Collectors.counting()))
               .entrySet().stream()
               .filter(i->i.getValue()>1)
               // .map(i->i.getValue()).distinct()  //if I sort like this the different number of names
               // .collect(Collectors.toMap())        //then how to collect them back to map???
               .max(Comparator.comparingLong(i-> i.getValue()))
               .map(name->name.getKey());

               // .max(Map.Entry.comparingByValue())
               // .map(name->name.getKey());
    }

    @Override
    public List<User> filterBy(final List<User> users, final Predicate<User>... predicates) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public String convertTo(final List<User> users, final String delimiter, final Function<User, String> mapFun) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Map<Privilege, List<User>> groupByPrivileges(List<User> users) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Map<String, Long> getNumberOfLastNames(final List<User> users) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
