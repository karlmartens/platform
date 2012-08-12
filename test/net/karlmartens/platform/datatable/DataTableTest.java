/**
 *   Copyright 2012 Karl Martens
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *       
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   net.karlmartens.platform, is a library of shared basic utility classes
 */

package net.karlmartens.platform.datatable;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static net.karlmartens.platform.datatable.DataTableColumn.Role.DATA;
import static net.karlmartens.platform.datatable.DataTableColumn.Role.DOMAIN;
import static net.karlmartens.platform.datatable.DataTableColumn.Type.BOOLEAN;
import static net.karlmartens.platform.datatable.DataTableColumn.Type.DATE;
import static net.karlmartens.platform.datatable.DataTableColumn.Type.DATE_TIME;
import static net.karlmartens.platform.datatable.DataTableColumn.Type.NUMBER;
import static net.karlmartens.platform.datatable.DataTableColumn.Type.STRING;
import static net.karlmartens.platform.datatable.DataTableColumn.Type.TIME_OF_DAY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import net.karlmartens.platform.TestSummarizer;
import net.karlmartens.platform.util.Range;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataTableTest {

  @Test
  public void test_addColumn_type() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);
    table.addColumn(NUMBER);

    assertEquals(TestSummarizer.lines(//
        "{", //
        "  \"cols\": [", //
        "    {", //
        "      \"type\": \"string\"", //
        "    },", //
        "    {", //
        "      \"type\": \"number\"", //
        "    }", //
        "  ],", //
        "  \"rows\": []", //
        "}"), createGson().toJson(table.toJson()));
  }

  @Test
  public void test_addColumn_type_label() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING, "Column A");
    table.addColumn(NUMBER, "Column B");

    assertEquals(TestSummarizer.lines(//
        "{", //
        "  \"cols\": [", //
        "    {", //
        "      \"label\": \"Column A\",", //
        "      \"type\": \"string\"", //
        "    },", //
        "    {", //
        "      \"label\": \"Column B\",", //
        "      \"type\": \"number\"", //
        "    }", //
        "  ],", //
        "  \"rows\": []", //
        "}"), createGson().toJson(table.toJson()));
  }

  @Test
  public void test_addColumn_type_label_id() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING, "Column A", "A");
    table.addColumn(NUMBER, "Column B", "B");
    table.addColumn(BOOLEAN, "Column C", "C");
    table.addColumn(DATE, "Column D", "D");
    table.addColumn(TIME_OF_DAY, "Column E", "E");
    table.addColumn(DATE_TIME, "Column F", "F");

    assertEquals(TestSummarizer.lines(//
        "{", //
        "  \"cols\": [", //
        "    {", //
        "      \"id\": \"A\",", //
        "      \"label\": \"Column A\",", //
        "      \"type\": \"string\"", //
        "    },", //
        "    {", //
        "      \"id\": \"B\",", //
        "      \"label\": \"Column B\",", //
        "      \"type\": \"number\"", //
        "    },", //
        "    {", //
        "      \"id\": \"C\",", //
        "      \"label\": \"Column C\",", //
        "      \"type\": \"boolean\"", //
        "    },", //
        "    {", //
        "      \"id\": \"D\",", //
        "      \"label\": \"Column D\",", //
        "      \"type\": \"date\"", //
        "    },", //
        "    {", //
        "      \"id\": \"E\",", //
        "      \"label\": \"Column E\",", //
        "      \"type\": \"timeOfDay\"", //
        "    },", //
        "    {", //
        "      \"id\": \"F\",", //
        "      \"label\": \"Column F\",", //
        "      \"type\": \"dateTime\"", //
        "    }", //
        "  ],", //
        "  \"rows\": []", //
        "}"), createGson().toJson(table.toJson()));
  }

  @Test
  public void test_addColumn_DataTableColumn() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(STRING)//
        .id("A")//
        .label("Column A")//
        .role(DOMAIN));
    table.addColumn(new DataTableColumn(NUMBER)//
        .id("B")//
        .label("Column B")//
        .pattern("#,##0")//
        .role(DATA));

    assertEquals(TestSummarizer.lines(//
        "{", //
        "  \"cols\": [", //
        "    {", //
        "      \"id\": \"A\",", //
        "      \"label\": \"Column A\",", //
        "      \"role\": \"domain\",", //
        "      \"type\": \"string\"", //
        "    },", //
        "    {", //
        "      \"id\": \"B\",", //
        "      \"label\": \"Column B\",", //
        "      \"pattern\": \"#,##0\",", //
        "      \"role\": \"data\",", //
        "      \"type\": \"number\"", //
        "    }", //
        "  ],", //
        "  \"rows\": []", //
        "}"), createGson().toJson(table.toJson()));
  }

  @Test
  public void test_addRow_value() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);
    table.addColumn(NUMBER);
    table.addColumn(BOOLEAN);
    table.addColumn(DATE);
    table.addColumn(TIME_OF_DAY);
    table.addColumn(DATE_TIME);

    table.addRow("Red", 10, TRUE, new LocalDate(2012, 1, 1), new LocalTime(13,
        23, 15, 37), new LocalDateTime(2012, 1, 1, 13, 23, 15, 37));
    table.addRow("Blue", 15.7, FALSE);

    assertEquals(TestSummarizer.lines(//
        "{", //
        "  \"cols\": [", //
        "    {", //
        "      \"type\": \"string\"", //
        "    },", //
        "    {", //
        "      \"type\": \"number\"", //
        "    },", //
        "    {", //
        "      \"type\": \"boolean\"", //
        "    },", //
        "    {", //
        "      \"type\": \"date\"", //
        "    },", //
        "    {", //
        "      \"type\": \"timeOfDay\"", //
        "    },", //
        "    {", //
        "      \"type\": \"dateTime\"", //
        "    }", //
        "  ],", //
        "  \"rows\": [", //
        "    {", //
        "      \"c\": [", //
        "        {", //
        "          \"v\": \"Red\"", //
        "        },", //
        "        {", //
        "          \"v\": 10", //
        "        },", //
        "        {", //
        "          \"v\": true", //
        "        },", //
        "        {", //
        "          \"v\": {", //
        "            \"year\": 2012,", //
        "            \"month\": 1,", //
        "            \"day\": 1", //
        "          }", //
        "        },", //
        "        {", //
        "          \"v\": {", //
        "            \"hour\": 13,", //
        "            \"minute\": 23,", //
        "            \"second\": 15,", //
        "            \"millis\": 37", //
        "          }", //
        "        },", //
        "        {", //
        "          \"v\": {", //
        "            \"year\": 2012,", //
        "            \"month\": 1,", //
        "            \"day\": 1,", //
        "            \"hour\": 13,", //
        "            \"minute\": 23,", //
        "            \"second\": 15,", //
        "            \"millis\": 37", //
        "          }", //
        "        }", //
        "      ]", //
        "    },", //
        "    {", //
        "      \"c\": [", //
        "        {", //
        "          \"v\": \"Blue\"", //
        "        },", //
        "        {", //
        "          \"v\": 15.7", //
        "        },", //
        "        {", //
        "          \"v\": false", //
        "        },", //
        "        {},", //
        "        {},", //
        "        {}", //
        "      ]", //
        "    }", //
        "  ]", //
        "}"), createGson().toJson(table.toJson()));
  }

  @Test
  public void test_addRow_DataTableCell() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);
    table.addColumn(NUMBER);

    table.addRow(new DataTableCell()//
        .value("Green")//
        .formattedValue("G"), 10.23);

    assertEquals(TestSummarizer.lines(//
        "{", //
        "  \"cols\": [", //
        "    {", //
        "      \"type\": \"string\"", //
        "    },", //
        "    {", //
        "      \"type\": \"number\"", //
        "    }", //
        "  ],", //
        "  \"rows\": [", //
        "    {", //
        "      \"c\": [", //
        "        {", //
        "          \"v\": \"Green\",", //
        "          \"f\": \"G\"", //
        "        },", //
        "        {", //
        "          \"v\": 10.23", //
        "        }", //
        "      ]", //
        "    }", //
        "  ]", //
        "}"), createGson().toJson(table.toJson()));
  }

  @Test
  public void test_addRows_DataTableRow() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);
    table.addColumn(STRING);

    final DataTableRow r0 = new DataTableRow(2);
    r0.cell(0).value("Red");
    r0.cell(1).value("Apple");

    final DataTableRow r1 = new DataTableRow(2);
    r1.cell(0).value("Green");

    table.addRows(r0, r1);

    assertEquals(TestSummarizer.lines(//
        "{", //
        "  \"cols\": [", //
        "    {", //
        "      \"type\": \"string\"", //
        "    },", //
        "    {", //
        "      \"type\": \"string\"", //
        "    }", //
        "  ],", //
        "  \"rows\": [", //
        "    {", //
        "      \"c\": [", //
        "        {", //
        "          \"v\": \"Red\"", //
        "        },", //
        "        {", //
        "          \"v\": \"Apple\"", //
        "        }", //
        "      ]", //
        "    },", //
        "    {", //
        "      \"c\": [", //
        "        {", //
        "          \"v\": \"Green\"", //
        "        },", //
        "        {}", //
        "      ]", //
        "    }", //
        "  ]", //
        "}"), createGson().toJson(table.toJson()));
  }

  @Test
  public void test_addRows_int() throws Exception {
    final DataTable table = new DataTable();
    table.addRows(3);

    assertEquals(TestSummarizer.lines(//
        "{", //
        "  \"cols\": [],", //
        "  \"rows\": [", //
        "    {", //
        "      \"c\": []", //
        "    },", //
        "    {", //
        "      \"c\": []", //
        "    },", //
        "    {", //
        "      \"c\": []", //
        "    }", //
        "  ]", //
        "}"), createGson().toJson(table.toJson()));
  }

  @Test
  public void test_getColumnId() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING, null, "A");
    table.addColumn(STRING, null, "B");

    assertEquals("A", table.getColumnId(0));
    assertEquals("B", table.getColumnId(1));
  }

  @Test
  public void test_getColumnLabel() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING, "Column A");
    table.addColumn(STRING, "Column B");

    assertEquals("Column A", table.getColumnLabel(0));
    assertEquals("Column B", table.getColumnLabel(1));
  }

  @Test
  public void test_getColumnPattern() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(NUMBER).pattern("#,##0.00"));
    table.addColumn(new DataTableColumn(NUMBER).pattern("0.00"));

    assertEquals("#,##0.00", table.getColumnPattern(0));
    assertEquals("0.00", table.getColumnPattern(1));
  }

  @Test
  public void test_getColumnRange_string() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);

    table.addRow("Oranges");
    table.addRow("Apple");
    table.addRow("Banana");
    table.addRow("Grape");
    table.addRow("Cherry");

    final Range range = table.getColumnRange(0);
    assertEquals(range.minimum(), "Apple");
    assertEquals(range.maximum(), "Oranges");
  }

  @Test
  public void test_getColumnRange_number() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(NUMBER);

    table.addRow(34);
    table.addRow(27.6);
    table.addRow(10.12);
    table.addRow(4);
    table.addRow(97);

    final Range range = table.getColumnRange(0);
    assertEquals(4.0, ((Number) range.minimum()).doubleValue(), 0.00001);
    assertEquals(97.0, ((Number) range.maximum()).doubleValue(), 0.00001);
  }

  @Test
  public void test_getColumnRange_date() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(DATE);

    table.addRow(new LocalDate(2010, 2, 1));
    table.addRow(new LocalDate(2005, 1, 31));
    table.addRow(new LocalDate(2010, 2, 24));
    table.addRow(new LocalDate(2005, 1, 15));
    table.addRow(new LocalDate(2006, 3, 17));

    final Range range = table.getColumnRange(0);
    assertEquals(new LocalDate(2005, 1, 15), range.minimum());
    assertEquals(new LocalDate(2010, 2, 24), range.maximum());
  }

  @Test
  public void test_getColumnRange_timeOfDay() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(TIME_OF_DAY);

    table.addRow(new LocalTime(12, 10, 30, 447));
    table.addRow(new LocalTime(0, 5, 59, 999));
    table.addRow(new LocalTime(12, 15, 0, 0));
    table.addRow(new LocalTime(12, 15, 1, 0));
    table.addRow(new LocalTime(7, 34, 15, 0));

    final Range range = table.getColumnRange(0);
    assertEquals(new LocalTime(0, 5, 59, 999), range.minimum());
    assertEquals(new LocalTime(12, 15, 1, 0), range.maximum());
  }

  @Test
  public void test_getColumnRange_dateTime() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(DATE_TIME);

    table.addRow(new LocalDateTime(2012, 6, 1, 12, 10, 30, 447));
    table.addRow(new LocalDateTime(2013, 1, 1, 0, 5, 59, 999));
    table.addRow(new LocalDateTime(2012, 6, 1, 12, 15, 0, 0));
    table.addRow(new LocalDateTime(2010, 2, 15, 12, 15, 1, 0));
    table.addRow(new LocalDateTime(2011, 1, 1, 7, 34, 15, 0));

    final Range range = table.getColumnRange(0);
    assertEquals(new LocalDateTime(2010, 2, 15, 12, 15, 1, 0), range.minimum());
    assertEquals(new LocalDateTime(2013, 1, 1, 0, 5, 59, 999), range.maximum());
  }

  @Test
  public void test_getColumnRole() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(STRING).role(DOMAIN));
    table.addColumn(new DataTableColumn(NUMBER).role(DATA));

    assertEquals(DOMAIN, table.getColumnRole(0));
    assertEquals(DATA, table.getColumnRole(1));
  }

  @Test
  public void test_getColumnType() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);
    table.addColumn(NUMBER);

    assertEquals(STRING, table.getColumnType(0));
    assertEquals(NUMBER, table.getColumnType(1));
  }

  @Test
  public void test_getFilteredRows_singleFilter() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);

    table.addRow("Oranges");
    table.addRow("Apple");
    table.addRow("Banana");
    table.addRow("Grape");
    table.addRow("Cherry");

    final int[] indices = table.getFilteredRows(new ValueDataTableFilter(0,
        "Apple", "Grape"));
    assertArrayEquals(new int[] { 1, 3 }, indices);
  }

  @Test
  public void test_getFilteredRows_multipleFilters() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);
    table.addColumn(NUMBER);

    table.addRow("Oranges", 3);
    table.addRow("Apple", 5);
    table.addRow("Banana", 2);
    table.addRow("Grape", 4);
    table.addRow("Cherry", 1);

    final int[] indices = table.getFilteredRows(new ValueDataTableFilter(0,
        "Apple", "Grape"), new RangeDataTableFilter(NUMBER, 1, 1, 5));
    assertArrayEquals(new int[] { 3 }, indices);
  }

  @Test
  public void test_getFormattedValue_default_string() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);
    table.addRow("Apple");
    table.addRows(1);
    assertEquals("Apple", table.getFormattedValue(0, 0));
    assertEquals("", table.getFormattedValue(1, 0));
  }

  @Test
  public void test_getFormattedValue_default_number() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(NUMBER);
    table.addRow(10);
    table.addRows(1);
    assertEquals("10.00", table.getFormattedValue(0, 0));
    assertEquals("", table.getFormattedValue(1, 0));
  }

  @Test
  public void test_getFormattedValue_default_boolean() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(BOOLEAN);
    table.addRow(TRUE);
    table.addRows(1);
    assertEquals("true", table.getFormattedValue(0, 0));
    assertEquals("", table.getFormattedValue(1, 0));
  }

  @Test
  public void test_getFormattedValue_default_date() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(DATE);
    table.addRow(new LocalDate(2012, 1, 15));
    table.addRows(1);
    assertEquals("2012-01-15", table.getFormattedValue(0, 0));
    assertEquals("", table.getFormattedValue(1, 0));
  }

  @Test
  public void test_getFormattedValue_default_timeOfDay() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(TIME_OF_DAY);
    table.addRow(new LocalTime(16, 30, 27, 999));
    table.addRow(new LocalTime(1, 30, 27, 999));
    table.addRows(1);
    assertEquals("16:30", table.getFormattedValue(0, 0));
    assertEquals("01:30", table.getFormattedValue(1, 0));
    assertEquals("", table.getFormattedValue(2, 0));
  }

  @Test
  public void test_getFormattedValue_default_dateTime() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(DATE_TIME);
    table.addRow(new LocalDateTime(2012, 1, 15, 1, 30, 27, 999));
    table.addRow(new LocalDateTime(2012, 1, 15, 16, 30, 27, 999));
    table.addRows(1);
    assertEquals("2012-01-15 01:30", table.getFormattedValue(0, 0));
    assertEquals("2012-01-15 16:30", table.getFormattedValue(1, 0));
    assertEquals("", table.getFormattedValue(2, 0));
  }

  @Test
  public void test_getFormattedValue_pattern_string() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(STRING).pattern("kk:mm"));
    table.addRow("Apple");
    table.addRows(1);
    assertEquals("Apple", table.getFormattedValue(0, 0));
    assertEquals("", table.getFormattedValue(1, 0));
  }

  @Test
  public void test_getFormattedValue_pattern_number() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(NUMBER).pattern("#,##0"));
    table.addRow(10);
    table.addRows(1);
    assertEquals("10", table.getFormattedValue(0, 0));
    assertEquals("", table.getFormattedValue(1, 0));
  }

  @Test
  public void test_getFormattedValue_pattern_boolean() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(BOOLEAN).pattern("yyyy-MM-dd"));
    table.addRow(TRUE);
    table.addRows(1);
    assertEquals("true", table.getFormattedValue(0, 0));
    assertEquals("", table.getFormattedValue(1, 0));
  }

  @Test
  public void test_getFormattedValue_pattern_date() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(DATE).pattern("MMM d, yyyy"));
    table.addRow(new LocalDate(2012, 1, 15));
    table.addRows(1);
    assertEquals("Jan 15, 2012", table.getFormattedValue(0, 0));
    assertEquals("", table.getFormattedValue(1, 0));
  }

  @Test
  public void test_getFormattedValue_pattern_timeOfDay() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(TIME_OF_DAY).pattern("kk:mm:ss.SSS"));
    table.addRow(new LocalTime(16, 30, 27, 999));
    table.addRow(new LocalTime(1, 30, 27, 999));
    table.addRows(1);
    assertEquals("16:30:27.999", table.getFormattedValue(0, 0));
    assertEquals("01:30:27.999", table.getFormattedValue(1, 0));
    assertEquals("", table.getFormattedValue(2, 0));
  }

  @Test
  public void test_getFormattedValue_pattern_dateTime() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(new DataTableColumn(DATE_TIME)
        .pattern("MMM d, yyyy kk:mm:ss.SSS"));
    table.addRow(new LocalDateTime(2012, 1, 15, 1, 30, 27, 999));
    table.addRow(new LocalDateTime(2012, 1, 15, 16, 30, 27, 999));
    table.addRows(1);
    assertEquals("Jan 15, 2012 01:30:27.999", table.getFormattedValue(0, 0));
    assertEquals("Jan 15, 2012 16:30:27.999", table.getFormattedValue(1, 0));
    assertEquals("", table.getFormattedValue(2, 0));
  }

  @Test
  public void test_getFormattedValue_specified_string() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(STRING);
    table.addRow(new DataTableCell().value("Apple").formattedValue("A"));
    assertEquals("A", table.getFormattedValue(0, 0));
  }

  @Test
  public void test_getFormattedValue_specified_number() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(NUMBER);
    table.addRow(new DataTableCell().value(10).formattedValue("A"));
    assertEquals("A", table.getFormattedValue(0, 0));
  }

  @Test
  public void test_getFormattedValue_specified_boolean() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(BOOLEAN);
    table.addRow(new DataTableCell().value(TRUE).formattedValue("Y"));
    assertEquals("Y", table.getFormattedValue(0, 0));
  }

  @Test
  public void test_getFormattedValue_specified_date() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(DATE);
    table.addRow(new DataTableCell().value(new LocalDate(2012, 1, 15))
        .formattedValue("20120115"));
    assertEquals("20120115", table.getFormattedValue(0, 0));
  }

  @Test
  public void test_getFormattedValue_specified_timeOfDay() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(TIME_OF_DAY);
    table.addRow(new DataTableCell().value(new LocalTime(16, 30, 27, 999))
        .formattedValue("163027999"));
    assertEquals("163027999", table.getFormattedValue(0, 0));
  }

  @Test
  public void test_getFormattedValue_specified_dateTime() throws Exception {
    final DataTable table = new DataTable();
    table.addColumn(DATE_TIME);
    table.addRow(new DataTableCell().value(
        new LocalDateTime(2012, 1, 15, 1, 30, 27, 999)).formattedValue(
        "20120115013027999"));
    assertEquals("20120115013027999", table.getFormattedValue(0, 0));
  }

  private Gson createGson() {
    return new GsonBuilder()//
        .setPrettyPrinting()//
        .create();
  }

}
