function getAllEmployees() {
    $.ajax({
        url:window.location+'employees',
        method: 'GET',
        dataType: 'json',
        data: {_embedded:{ employeeDtoList: new Array()}},
        success: function (data) {
            var tableBody = $('.tblEmployee tbody');
            tableBody.empty();
            $(data._embedded.employeeDtoList).each(function (index, element) {
               loadEmployeesTable(tableBody,element);
            });
        },
        error: function (error) {
            showMessage(error, 'danger');
        }
    });
}

function getRoles() {
    $.ajax({
        url: window.location+'roles',
        method: 'GET',
        dataType: 'json',
        data: {_embedded:{ roleDtoList: Array()}},
        success: function (data) {
            var optionSelect = $('#employee-role');
            var optionSelectFind = $('#employee-role-find');
            $(data._embedded.roleDtoList).each(function (index, element) {
                loadRoleSelect(optionSelect,element);
                loadRoleSelect(optionSelectFind,element);
            });
        },
        error: function (error) {
            showMessage(error, 'danger');
        }
    });
}

function getEmployeeById(id) {
    $.ajax({
        url: window.location+'employees/' + id,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            var tableBody = $('.tblEmployee tbody');
            tableBody.empty();
            loadEmployeesTable(tableBody,data);

        },
        error: function (error) {
            var message=errorHandler(error);
            showMessage(message, 'danger');
            console.log("Fail: ", error);
        }
    });
}

function getEmployeesByRoleId(role) {
    $.ajax({
        url: window.location+'employees/roleid/' + role,
        method: 'GET',
        dataType: 'json',
        data: {_embedded:{ employeeDtoList: new Array()}},
        success: function (data) {
             if (data._embedded){
                var tableBody = $('.tblEmployee tbody');
                tableBody.empty();
                $(data._embedded.employeeDtoList).each(function (index, element) {
                    loadEmployeesTable(tableBody, element);
                });
             } else {
                var message=errorHandler('There is no employees employees with Role ID ' + role);
                showMessage(message, 'danger');
             }
        },
        error: function (error) {
            var message=errorHandler(error);
            showMessage(message, 'danger');
            console.log("Fail: ", error);
        }
    });
}

function getEmployeesByRoleTittle(role) {
    $.ajax({
        url: window.location+'employees/role/' + role,
        method: 'GET',
        dataType: 'json',
        data: {_embedded:{ employeeDtoList: new Array()}},
        success: function (data) {
            if (data._embedded){
                var tableBody = $('.tblEmployee tbody');
                tableBody.empty();
                $(data._embedded.employeeDtoList).each(function (index, element) {
                    loadEmployeesTable(tableBody, element);
                });
            }else{
                var message=errorHandler('There is no employees with Role Tittle "' +role + '"');
                showMessage(message, 'danger');
            }
        },
        error: function (error) {
            var message=errorHandler(error);
            showMessage(message, 'danger');
            console.log("Fail: ", error);
        }
    });
}

function addEmployee(dynamicURL,methodName,employee){
    $.ajax({
        url: dynamicURL,
        method: methodName,
        data: JSON.stringify(employee),
        dataType: 'json',
        contentType: "application/json",
        success: function (data) {
            var message='Employee with id '+ data.id + ' saved successfully';
            showMessage(message, 'success');
            resetForm();
            resetEmployeeRoleSelect();
            getAllEmployees();
        },
        error: function (error) {
            var message=errorHandler('Fill in all search fields, please');
            showMessage(message, 'danger');
        }
    });
}

function updateEmployee(id){
    $.ajax({
        url: window.location+'employees/'+id,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            setEmployeeForm(data);
        },
        error: function (error) {
            showMessage(error, 'danger');
        }
    });
}

function deleteEmployee(id){
    if (confirm('Are you sure you want to delete this employee?')) {
        $.ajax({
              url: window.location+'employees/'+id,
              method: 'DELETE',
              success: function () {
                  var message='Employee with id '+ id + ' has been deleted';
                  showMessage(message, 'success');
                  resetForm();
                  resetEmployeeRoleSelect();
                  getAllEmployees();
              },
              error: function (error) {
                  showMessage(error, 'danger');
              }
          });
    }
}

function loadEmployeesTable(tableBody, employee){
    tableBody.append('<tr><td>'+employee.id+'</td><td>'+employee.firstName+'</td>'
        + '<td>'+employee.lastName+'</td><td>'+employee.jobTitle+'</td><td>'+employee.annualSalary+'</td>'
        + '<td><button class="btn btn-link text-success" onclick = "updateEmployee('+employee.id+')">Update</button> | '
        + '<button class="btn btn-link text-danger" onclick = "deleteEmployee('+employee.id+')">Delete</button></td></tr>');

}

function loadRoleSelect (select, role){
    select.append($('<option>').val(role.id).text(role.jobTitle));
}

function setEmployeeForm(employee){
    $('.employee-id').val(employee.id);
    $('.employee-firstName').val(employee.firstName);
    $('.employee-lastName').val(employee.lastName);
    $('#employee-role').find('option').removeAttr('selected');
    $('#employee-role').find('option:contains('+employee.jobTitle+')').attr('selected',true);
}

