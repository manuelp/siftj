package me.manuelp.siftj;

import me.manuelp.siftj.data.Sex;
import me.manuelp.siftj.sql.SqlFilter;
import me.manuelp.siftj.sql.SqlNameFilter;
import me.manuelp.siftj.sql.SqlPotentialFriendFilter;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static fj.P.p;
import static me.manuelp.siftj.data.AgeFilter.ageFilter;
import static me.manuelp.siftj.data.Range.range;
import static me.manuelp.siftj.sql.ParamIndex.paramIndex;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SqlFilterTest {
  @Test
  public void canGenerateWhereClause() {
    SqlFilter f = ageFilter(12);

    assertEquals("t.age=?", f.whereClause().getClause());
  }

  @Test
  public void canBindStatementValues() throws SQLException {
    PreparedStatement statement = mock(PreparedStatement.class);
    SqlFilter f = new SqlNameFilter("t", "Larry");

    f.bindParameter().f(p(paramIndex(1), statement));

    verify(statement).setString(1, "Larry");
  }

  @Test
  public void canGenerateComplexWhereClauses() {
    SqlFilter f = new SqlPotentialFriendFilter(range(18, 45), Sex.FEMALE, "p");

    assertEquals("(p.age BETWEEN ? AND ?) AND p.sex=?", f.whereClause()
        .getClause());
  }

  @Test
  public void canBindStatementValuesForComplexWhereClauses()
      throws SQLException {
    PreparedStatement statement = mock(PreparedStatement.class);
    SqlFilter f = new SqlPotentialFriendFilter(range(18, 45), Sex.FEMALE, "p");

    f.bindParameter().f(p(paramIndex(5), statement));

    verify(statement).setInt(5, 18);
    verify(statement).setInt(5 + 1, 45);
    verify(statement).setString(5 + 2, Sex.FEMALE.name());
  }
}