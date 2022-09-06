GET       /employees                    # get employee list
GET       /employees/1# get a specific employee by ID
GET       /employees?gender=male        # get all male employees
POST      /employees                    # add an employeeresponse status 201 created
PUT       /employees/1# update an employee
DELETE    /employees/1# delete an employeeresponse status 204 no content
GET       /employees?page=1&pageSize=5# Page query, page equals 1, pageSize equals 5