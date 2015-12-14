    angular.module('myApp123',[]);
//control login
angular.module('myApp123').controller('login', function($rootScope,$scope,$http){
	$rootScope.loginFlag=true;
	
	$scope.login = function(){
		
		$http.get("coupon/admin/login?name="+$scope.name+
				"&password="+$scope.password).success(function(response){
			if(response=="getin"){
				$rootScope.facadeFlag=true;
				$rootScope.loginFlag=false;
				
			}	
			
		});

	};
});

angular.module('myApp123').controller('incomes', function($rootScope,$scope,$http){
	$scope.showIncomeByName = function(){
		
			$http.get("rest/income/view/byName/"+$scope.incomName).success(function(response){
				$rootScope.incomes=response.incomeWrapper;
		})
		
		
	}
});



//company controller
angular.module('myApp123').controller('showCompanies', function($rootScope,$scope, $http){
		$http.post("coupon/admin/getCompanie").success(function(response){
			if(response.company[0].id=="-1"){
				$scope.error=response.company[0].message;
				$scope.allItemsResult="";
			}
			else{
				$scope.allItemsResult=response.company;
				$scope.error="";
			}
	});
	$scope.logout = function(){
		$http.post("coupon/admin/logout").success(function(response){});
		$rootScope.loginFlag=true;
		$rootScope.showCompaniesFlag=false;
		window.location="index.html";
	};
	$scope.ShowCustomer = function(){
		$rootScope.showCompaniesFlag=false;
		$rootScope.showCustomersFlag=true;
		$http.get("coupon/admin/getcustomers").success(function(response){
			$rootScope.allCustomers=response.customer;
		});
	};
	$scope.del= function(x){
		$http.post("coupon/admin/deleteCompany?id="+x).success(function(response){
			if(response=="ok")
				$http.get("coupon/admin/getCompanie").success(function(response){
					if(response.company[0].id=="-1"){
						$rootScope.error=response.company[0].message;
						$rootScope.allItemsResult="";
					}
					else{
						$rootScope.allItemsResult=""
						$rootScope.error="";
						$rootScope.allItemsResult=response.company;
					}
				});
			else
				$scope.error=response;
		});
	};
	$scope.add=function(){
		$http.post("coupon/admin/addCompany?name="+$scope.name+
				"&password="+$scope.password+
				"&email="+$scope.email).success(function(response){	
					
					if(response=="ok")
						$http.get("coupon/admin/getCompanie").success(function(response){
							if(response.company[0].id=="-1"){
								$rootScope.error=response.company[0].message;
								$rootScope.allItemsResult="";
							}
							else{
								$rootScope.error="";
								$rootScope.allItemsResult=""
								$rootScope.allItemsResult=response.company;
							}
						});
					else
						$scope.error=response;
				
				});
		
	
	};
	$scope.update=function(id,name,password,email){
		$rootScope.showCompaniesFlag=false;
		$rootScope.updateCompanyFlag=true;
		$rootScope.Uid=id;
		$rootScope.Uname=name;

		$rootScope.Upassword=password;
		$rootScope.Uemail=email;

	
	}
	
	
});


angular.module('myApp123').controller('updateCompany', function($rootScope,$scope, $http){
	$scope.update=function(id){
		$http.post("coupon/admin/updateCompany?id="+id+
				"&name="+$scope.Uname+
				"&password="+$scope.Upassword+
				"&email="+$scope.Uemail).success(function(response){
					
					if(response=="ok"){
						$rootScope.showCompaniesFlag=true;
						$rootScope.updateCompanyFlag=false;
				$http.get("coupon/admin/getCompanie").success(function(response){
					if(response.company[0].id=="-1"){
						$rootScope.error=response.company[0].message;
						$rootScope.allItemsResult="";
					}
					else{
						$rootScope.error="";
						$rootScope.allItemsResult="";
						$rootScope.allItemsResult=response.company;
					}
				});
			}
			else
				$scope.error=response;
		});
	};
});



//sum controller
angular.module('myApp123').controller('facade', function($rootScope, $scope, $http){
	$rootScope.incomesByNameFlag=false;
	$rootScope.incomesFlag=false;

	$scope.showIncomeByName = function(){
		if($rootScope.incomesByNameFlag==false){
			$rootScope.incomesByNameFlag=true;
		}
		else
			$rootScope.incomesByNameFlag=false;

	}
	
	$scope.showIncomes = function(){
		if($rootScope.incomesFlag==false){
			$rootScope.incomesFlag=true;
			$http.get("rest/income/view/all").success(function(response){
				$rootScope.incomes=response.incomeWrapper;
		});}
		
		else
			$rootScope.incomesFlag=false;
	}
	
	
	$scope.logout = function(){
		$rootScope.facadeFlag=false;
		$rootScope.loginFlag=true;
		$http.post("coupon/admin/logout").success(function(response){});
		window.location="index.html";

	};
	$scope.showCompanies = function(){
		$http.get("coupon/admin/getCompanie").success(function(response){
			if(response.company[0].id=="-1"){
				$rootScope.error=response.company[0].message;
				$rootScope.allItemsResult="";
			}
			else{
				$rootScope.error="";
				$rootScope.allItemsResult=response.company;
			}
		});
		$rootScope.facadeFlag=false;
		$rootScope.showCompaniesFlag=true;
	};
	$scope.showCustomers = function(){
		$http.get("coupon/admin/getcustomers").success(function(response){
			if(response.customer[0].id=="-1"){
				$rootScope.errorcust=response.customer[0].message;
				$rootScope.allCustomer="";
			}
			else{
				$rootScope.errorcust="";
				$rootScope.allCustomers=response.customer;
			}
		});
		$rootScope.facadeFlag=false;
		$rootScope.showCustomersFlag=true;
	};
	
	
});


angular.module('myApp123').controller('showCustomers', function($rootScope, $scope, $http){
	
	$scope.logout = function(){
		$rootScope.facadeFlag=false;
		$rootScope.loginFlag=true;
		$rootScope.showCustomersFlag=false;
		$http.post("coupon/admin/logout").success(function(response){});
		window.location="index.html";


	};
	$scope.showCompanies = function(){
		$http.get("coupon/admin/getCompanie").success(function(response){
			if(response.customer[0].id=="-1"){
				$rootScope.error=response.customer[0].message;
				$rootScope.allCustomer="";
			}
			else{
				$rootScope.error="";
				$rootScope.allItemsResult=response.company;
			}
		});
		$rootScope.showCompaniesFlag=true;
		$rootScope.showCustomersFlag=false;
	};
	
	$scope.add=function(){
		$http.post("coupon/admin/addCustomer?name="+$scope.name+
				"&password="+$scope.password+
				"&email="+$scope.email).success(function(response){
				if(response=="ok")
					$http.get("coupon/admin/getcustomers").success(function(response){
						if(response.customer[0].id=="-1"){
							$scope.errorcust=response.customer[0].message;
							$scope.allCustomer="";
						}
						else{
							$scope.errorcust="";
							$scope.allCustomers=response.customer;
						}
					});
				else
					$scope.errorcust=response;
		});
		
	};
	$scope.del= function(x){
		$http.post("coupon/admin/deleteCustomer?id="+x).success(function(response){
			if(response=="ok")
				$http.get("coupon/admin/getcustomers").success(function(response){
					if(response.customer[0].id=="-1"){
						$scope.errorcust=response.customer[0].message;
						$scope.allCustomer="";
					}
					else{
						$scope.errorcust="";
						$scope.allCustomers=response.customer;
					}
				});
			else
				$scope.errorcust=response;
		});
	};
	$scope.update=function(id,custName,password){
		$rootScope.Cname=custName;
		$rootScope.Cid=id;
		$rootScope.Cpassword=password;
		$rootScope.showCustomersFlag=false;
		$rootScope.updateCustomerFlag=true;
		
	};
	
});	
	
angular.module('myApp123').controller('updateCustomer', function($rootScope, $scope, $http){
	
	$scope.update=function(){
		$http.post("coupon/admin/updateCustomer?id="+$scope.Cid+
				"&name="+$scope.Cname+
				"&password="+$scope.Cpassword).success(function(response){
					
					if(response=="ok"){
						$rootScope.showCustomersFlag=true;
						$rootScope.updateCustomerFlag=false;
						$http.get("coupon/admin/getcustomers").success(function(response){
							if(response.customer[0].id=="-1"){
								$rootScope.errorcust=response.customer[0].message;
								$rootScope.allCustomer="";
							}
							else{
								$rootScope.errorcust="";
								$rootScope.allCustomers="";
								$rootScope.allCustomers=response.customer;
							}
						})}
					else
						$scope.errorcust=response;
				});
	};	
}
);