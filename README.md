# RestWebService
Rest Web Service that creates database from XML file. If you send post request, it creates response with certain parameters. 

I used only jdbc queries without any JPA.

When you launch jar you need to pass XML filename as command line parameter. Then app will create 2 tables in database according to this xml file. These tables are "Box" and "Item". "Box" contain boxID and containsIn, which shows which box contains current box. "Item" contains itemID, color and containsIn. Item can exist outside of any box. 

When you make post request with integer 'id' and string 'color', app searches for item with this color in a box with boxID equal to 'id', with nested search. In response you will get id of those items.

# Dependencies

Spring Boot, Spring JDBC.  
