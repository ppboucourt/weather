(function() {
    'use strict';

    angular
        .module('weatherApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', 'Weather'];

    function HomeController ($scope, Principal, LoginService, $state, $http, Weather) {
        var vm = this;

        vm.submit = submit;
        vm.validateCity = validateCity;
        vm.loadAll = loadAll;
        vm.submitRest = submitRest;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.form = {};
        vm.cityWeather = {};
        vm.weather = {};

        // $scope.$on('authenticationSuccess', function() {
        //     getAccount();
        // });

        function loadAll() {
            var url = 'http://api.wunderground.com/api/0febb2c6dfdd1e46/conditions/q/Miami.json';
            $.post( url, function( data ) {
                console.log('loaded data from ws:' + data.response);
                vm.weather = data.response.results;
            });
        }

        function submit() {
            if ($('#file').val()) {
                if (vm.cities) {
                    var url = 'http://api.wunderground.com/api/0febb2c6dfdd1e46/conditions/q/';
                    var city = vm.cities.split(' ')[0];
                    url += city + '.json';
                    $.post(url, function (data) {
                        console.log('Miami:' + data);
                        vm.weather = data.response.results;
                    });
                }else {
                    alert('Insert a City')
                }
            } else {
                alert('Upload Empty');
            }
            $state.go('home', null, { reload: 'weather' });
        }

        function submitRest() {
            Weather.cities({cities: vm.cities}, successWSCall);
            $state.reload;
        }

        function successWSCall(result) {
            console.log(result);
        }

        function validateCity() {
            if (vm.cities && vm.cities.length > 3) {
                var myregexp = /^([^,]+),[^(]*\(([^()]+)\)/;
                var match = myregexp.exec(vm.cities);
                if (match != null) {
                    vm.city = match[1];
                }
            }
        }

    }
})();
