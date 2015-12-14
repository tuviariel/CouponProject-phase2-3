angular.module('myApp123',[]);

var d=new Date();
var date = [d.getFullYear(), d.getMonth()+1, d.getDate()];

//Login panel
angular.module('myApp123').controller('loginController', function($rootScope, $scope,$http){
	//on load
	$rootScope.loginFlag=true;
	
	//$scope.loginResult="Waiting for correct log-in info...";
	
	//on click
	$scope.login = function(){
	$http.post("coupon/company/login?name="+$scope.name+"&password="+$scope.password).success(function(response){
		if(response=="Successful Company Login!! Welcome "+$scope.name+"!!"){
			$rootScope.myname=$scope.name;
			alert($scope.loginResult=response);
			$rootScope.loginFlag=false;
			$rootScope.companyFlag=true;
			$http.get("coupon/company/all").success(function(response){
				if(response.couponHTML[0].id=="-1"){
					$rootScope.error=response.couponHTML[0].message;
					$rootScope.allCoupons="";
				}
				else{
					$rootScope.allCoupons=response.couponHTML;
					$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
					$rootScope.header="All of Company's Coupons:";
					$rootScope.error="";
				}
			});
		}	
		else{
			alert($scope.loginResult=response);
			$scope.loginResult=response;
		}
	});
	
	};
});



//company controller
angular.module('myApp123').controller('companyController', function($rootScope,$scope,$http){
	
	
	$scope.showIncomes = function(){
		if($rootScope.incomesFlag==false){
			$rootScope.incomesFlag=true;
			$http.get("rest/income/view/byName/"+$rootScope.myname).success(function(response){
				$rootScope.incomes=response.incomeWrapper;
		});}
		
		else
			$rootScope.incomesFlag=false;
	}
	
	
	//logout
	$scope.logout = function(){
		$http.post("coupon/company/logout").success(function(response){});
		$rootScope.companyFlag=false;
		$rootScope.loginFlag=true;
		window.location="index.html";
	};

	//add coupon
	$scope.add=function(){
		$http.post("coupon/company/add?title="+$scope.title+
				"&jsdate="+$scope.HTMLdate+
				"&amount="+$scope.amount+
				"&type="+$scope.type+
				"&message="+$scope.message+
				"&price="+$scope.price+
				"&image="+$scope.image).success(function(response){						
					//$rootScope.endDatejs=request.getParameter($scope.endDate);			
					if(response=="ok"){
						$http.get("coupon/company/all").success(function(response){
							if(response.couponHTML[0].id=="-1"){
								$rootScope.error=response.couponHTML[0].message;
								$rootScope.allCoupons="";
							}
							else{//$scope.allCoupons="";
									$rootScope.error="";
									$rootScope.allCoupons=response.couponHTML;
									$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
									$rootScope.header="All of Company's Coupons:";
									$rootScope.error="the Coupon was successfully Added!!";
							}
						});
					$http.get("rest/income/add/"+$rootScope.myname).success(function(response){});
					$rootScope.incomesFlag=false;
					}
			else
				$rootScope.error=response;
			});
	};
		
    	//delete coupon
	$scope.del= function(x){
		$rootScope.incomesFlag=false;
		if(confirm($scope.confirm=("Are you sure you would like to Delete Coupon #"+x+" from the DataBase (can't be undone)?"))==true){
			$http.post("coupon/company/del?id="+x).success(function(response){
				
				if(response=="ok"){
					$http.get("coupon/company/all").success(function(response){
						if(response.couponHTML[0].id=="-1"){
							$rootScope.error=response.couponHTML[0].message;
							$rootScope.allCoupons="";
						}
						else{//$scope.allCoupons="";
							$rootScope.error="";
							$rootScope.allCoupons=response.couponHTML;
							$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
							$rootScope.header="All of Company's Coupons:";
							$rootScope.error="Coupon was successfully Deleted!!";
						}
					});
				}
				else
					$scope.error=response;
			});
		
		}
		else{$rootScope.error="";}
	};
	
	//update button
	$scope.update=function(id,title,amount,type,message,price,image,startDate){
		$rootScope.incomesFlag=false;
		$rootScope.companyFlag=false;
		$rootScope.updateFlag=true;
		$rootScope.byIdFlag=false;
		$rootScope.typeFlag=false;
		$rootScope.priceFlag=false;
		$rootScope.endDateFlag=false;
		
		$rootScope.UpdateError="";
		$rootScope.Cid=id;
		$rootScope.Ctitle=title;
		//$rootScope.DendDate=endDate;
		$rootScope.Camount=amount;
		$rootScope.Ctype=type;
		$rootScope.Cmessage=message;
		$rootScope.Cprice=price;
		$rootScope.Cimage=image;
		$rootScope.CstartDate=startDate;
	};
	//get by Id Coupon
	$scope.showById=function(){
		$rootScope.byIdFlag=true;
		$rootScope.typeFlag=false;
		$rootScope.priceFlag=false;
		$rootScope.endDateFlag=false;
	};
	//get by type Coupon
	$scope.showType=function(){
		$rootScope.typeFlag=true;
		$rootScope.byIdFlag=false;
		$rootScope.priceFlag=false;
		$rootScope.endDateFlag=false;
	};
	//get by price Coupon
	$scope.showPrice=function(){
		$rootScope.priceFlag=true;
		$rootScope.byIdFlag=false;
		$rootScope.typeFlag=false;
		$rootScope.endDateFlag=false;
	};
	//get by endDate Coupon
	$scope.showEndDate=function(){
		$rootScope.endDateFlag=true;
		$rootScope.byIdFlag=false;
		$rootScope.priceFlag=false;
		$rootScope.typeFlag=false;
	};
	
	//showAllCoupons
	$scope.all=function(){
		$http.get("coupon/company/all").success(function(response){
				if(response.couponHTML[0].id=="-1"){
					$rootScope.error=response.couponHTML[0].message;
					$rootScope.allCoupons="";
				}
				else{
					$rootScope.allCoupons=response.couponHTML;
					$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
					$rootScope.header="All of Company's Coupons:";
					$rootScope.error="";
				}
		});
		
		$rootScope.byIdFlag=false;
		$rootScope.typeFlag=false;
		$rootScope.priceFlag=false;
		$rootScope.endDateFlag=false;
	};
});

	



angular.module('myApp123').controller('updateController', function($rootScope,$scope, $http){
	$scope.Cupdate=function(){
		$rootScope.incomesFlag=false;
		$http.post("coupon/company/Cupdate?id="+$scope.Cid+
					"&title="+$scope.Ctitle+
					"&endDate="+$scope.CendDate+
					"&amount="+$scope.Camount+
					"&type="+$scope.Ctype+
					"&message="+$scope.Cmessage+
					"&price="+$scope.Cprice+
					"&image="+$scope.Cimage+
					"&startDate="+$scope.CstartDate).success(function(response){
			if(response=="ok"){
				
				$http.get("rest/income/update/"+$rootScope.myname).success(function(response){});
			
				$rootScope.companyFlag=true;
				$rootScope.updateFlag=false;
				$http.get("coupon/company/all").success(function(response){
					if(response.couponHTML[0].id=="-1"){
						$rootScope.error=response.couponHTML[0].message;
						$rootScope.allCoupons="";
					}
					else{
						$rootScope.allCoupons="";
						$rootScope.error="";
						$rootScope.allCoupons=response.couponHTML;
						$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
						$rootScope.header="All of Company's Coupons:";
						$rootScope.error="Coupon (#"+$rootScope.Cid+") was successfully Updated!!";
					}
				});
				}
			else
				$scope.UpdateError=response;
		});
	};
	//back
	$scope.back=function(){
		$rootScope.companyFlag=true;
		$rootScope.updateFlag=false;
		$http.get("coupon/company/all").success(function(response){
			if(response.couponHTML[0].id=="-1"){
				$rootScope.error=response.couponHTML[0].message;
				$rootScope.allCoupons="";
			}
			else{
				$rootScope.allCoupons="";
				$rootScope.allCoupons=response.couponHTML;
				$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
				$rootScope.header="All of Company's Coupons:";
				$rootScope.error="";
			}
		});

	}
	
});



//specified show controllers
angular.module('myApp123').controller('byIdController', function($rootScope,$scope, $http){
	$scope.byId=function(){
		$http.get("coupon/company/byId?id="+$scope.sid).success(function(response){
			if(response.couponHTML[0].id=="-1"){
				$rootScope.error=response.couponHTML[0].message;//"There is no Coupon with Id#"+$scope.sid+" in the Database.";
				$rootScope.allCoupons="";
			}
			else{
				$rootScope.allCoupons="";
				$rootScope.allCoupons=response.couponHTML;
				$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
				//$rootScope.byIdFlag=false;
				$rootScope.header="Selected Coupon (#"+$scope.sid+"):";
				$rootScope.error="";
			}
		});
	};
});



angular.module('myApp123').controller('typeController', function($rootScope,$scope, $http){
	$scope.type=function(){
		$http.get("coupon/company/type?type="+$scope.stype).success(function(response){
			if(response==null){
				$rootScope.error="There are no Coupons with "+$scope.stype+" Type in the Database.";
				$rootScope.allCoupons="";
			}
			else{
				$rootScope.allCoupons="";
				$rootScope.allCoupons=response.couponHTML;
				$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
				//$rootScope.typeFlag=false;
				$rootScope.header="Selected Coupon/s by Type ("+$scope.stype+"):";
				$rootScope.error="";
			}
		});
	};
});



angular.module('myApp123').controller('priceController', function($rootScope,$scope, $http){
	$scope.price=function(){
		$http.get("coupon/company/price?price="+$scope.sprice).success(function(response){
			if(response==null){
				$rootScope.error="There are no Coupons that cost less then "+$scope.sprice+"nis. in the Database.";
				$rootScope.allCoupons="";
			}
			else{
				$rootScope.allCoupons="";
				$rootScope.allCoupons=response.couponHTML;
				$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
				//$rootScope.priceFlag=false;
				$rootScope.header="Selected Coupon/s by Maximum Price (up to "+$scope.sprice+"nis):";
				$rootScope.error="";
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















angular.module('myApp123').controller('endDateController', function($rootScope,$scope, $http){
	$scope.endDate=function(){
		$http.get("coupon/company/endDate?&jsdate="+$scope.sHTMLdate).success(function(response){
			if(response==null){
				$rootScope.error="There are no Coupons that expire earlyer than "+$scope.sHTMLdate+" in the Database.";
				$rootScope.allCoupons="";
			}
			else{
				$rootScope.allCoupons="";
				$rootScope.allCoupons=response.couponHTML;
				$rootScope.today=date[0]+"-"+date[1]+"-"+date[2];
				//$rootScope.endDateFlag=false;
				$rootScope.header="Selected Coupon/s by Expiration-Date (up to "+$scope.sHTMLdate+"):";
				$rootScope.error="";
			}
		});
	};
});