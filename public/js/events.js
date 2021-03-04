$(document).ready(function () {
     getAllEmployees();
     getRoles();

    document.getElementById('employee-form').addEventListener('submit', function(e){
        var employee = {};
        var dynamicURL = '';
        var methodName = '';
        employee = getEmployee();
        if(employee.id){
            //update
            dynamicURL = window.location+'employees/'+employee.id;
            methodName = 'PUT';
        }else{
            //save
            dynamicURL = window.location+'employees';
            methodName = 'POST';
        }
        addEmployee(dynamicURL,methodName,employee);

        e.preventDefault();
    });

    document.getElementById('employee-all-btn').addEventListener('click', function(e){
        getAllEmployees();
        e.preventDefault();
    });

     document.getElementById('clear-btn').addEventListener('click', function(e){
        reset();
        e.preventDefault();
     });

     document.getElementById('employee-id-find-btn').addEventListener('click', function(e){
         var employeeId = $('#employee-id-find').val();
         if (employeeId.trim() != ''){
             getEmployeeById(employeeId);
         } else {
             var message=errorHandler('Fill in a Employee ID, please');
             showMessage(message, 'danger');
         }
         e.preventDefault();
     });

    document.getElementById('employee-role-find-btn').addEventListener('click', function(e){
      var employeeRoleId = $('#employee-role-find').val();
      if (employeeRoleId != ''){
          var employeeRoleTittle = $('#employee-role-find').find('option:selected').text();
          getEmployeesByRoleTittle(employeeRoleTittle);
      }
      else{
          var message=errorHandler('Fill in a Role Title, please');
          showMessage(message, 'danger');
      }
       e.preventDefault();
    });

    document.getElementById('employee-role-id-find-btn').addEventListener('click', function(e){
      var employeeRoleId = $('#employee-role-id-find').val();
      if (employeeRoleId.trim() != '' && employeeRoleId > 0 && employeeRoleId <= 11){
          getEmployeesByRoleId(employeeRoleId);
      }
      else{
          $('#employee-role-id-find').val('');
          var message=errorHandler('Fill in a valid Role ID, please');
          showMessage(message, 'danger');
      }
       e.preventDefault();
    });

});

function errorHandler(error) {
    var message;
    var statusErrorMap = {
      '400' : "Server understood the request, but request content was invalid.",
      '401' : "Unauthorized access.",
      '403' : "Forbidden resource can't be accessed.",
      '404' : "Resource not found",
      '405' : "Method Not Allowed",
      '406' : "Not Acceptable request header",
      '412' : "Precondition Failed",
      '415' : "Unsupported Media Type",
      '500' : "Internal server error",
      '501' : "Not Implemented",
      '503' : "Service unavailable"
    };
    if (error.status) {
        message =statusErrorMap[error.status];
        if(!message){
            message="Unknown Error \n.";
        }
    }else if(error.exception==='parsererror'){
        message="Error.\nParsing JSON Request failed.";
    }else if(error.exception==='timeout'){
        message="Request Time out.";
    }else if(error.exception==='abort'){
        message="Request was aborted by the server";
    }else if (error != ''){
        message=error;
    }else{
        message="Unknown Error \n.";
    }
    return message;
}

 function showMessage(message,cssClass){
    const div = document.createElement('div');
    div.className=`alert alert-${cssClass} mt-2`;
    div.appendChild(document.createTextNode(message));
    //MOSTRAR MENSAJE EN DOM
    const container = document.querySelector('.container');
    const app = document.querySelector('#employee-management');
    container.insertBefore(div, app);
    setTimeout(function(){
        document.querySelector('.alert').remove();
    }, 3000);
}

function reset(){
    resetForm();
    resetEmployeeRoleSelect();
    resetEmployeeRoleFindSelect();
    $('#employee-id-find').val('');
    $('#employee-role-id-find').val('');
}

function resetForm (){
    document.getElementById('employee-form').reset();
}

function resetEmployeeRoleSelect(){
    $('#employee-role').find('option').removeAttr('selected');
    $('#employee-role').val('');
}

function resetEmployeeRoleFindSelect(){
    $('#employee-role-find').find('option').removeAttr('selected');
    $('#employee-role-find').val('');
}

function getEmployee(){
    var employee ={};
    employee.id = $('.employee-id').val();
    employee.firstName = $('.employee-firstName').val();
    employee.lastName = $('.employee-lastName').val();
    employee.role = { id : $('#employee-role').val() };
    return employee;
}







