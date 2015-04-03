package me.manuelp.jfilter;

import com.googlecode.totallylazy.Predicate;
import me.manuelp.jfilter.data.Person;
import me.manuelp.jfilter.data.PotentialFriendFilter;
import me.manuelp.jfilter.data.Sex;
import org.junit.Test;

import static me.manuelp.jfilter.data.AgeFilter.ageFilter;
import static me.manuelp.jfilter.data.Range.range;
import static me.manuelp.jfilter.data.SexFilter.sexFilter;
import static org.junit.Assert.*;

public class FilterTest {
  @Test
  public void simpleFilters() {
    Person p = new Person("Name", "Surname", 18, Sex.FEMALE);

    assertTrue(ageFilter(18).match(p));
    assertTrue(sexFilter(Sex.FEMALE).match(p));
    assertFalse(sexFilter(Sex.MALE).match(p));
  }

  @Test
  public void filtersArePartiallyAppliedFunctions() {
    Person p = new Person("Name", "Surname", 18, Sex.FEMALE);
    Predicate<Person> f = ageFilter(18).fn();

    assertTrue(f.matches(p));
  }

  @Test
  public void thereCanBeComplexFilters() {
    Person p = new Person("Name", "Surname", 18, Sex.FEMALE);

    assertTrue(new PotentialFriendFilter(range(18, 35), Sex.FEMALE).match(p));
    assertFalse(new PotentialFriendFilter(range(20, 35), Sex.FEMALE).match(p));
  }

}