(function() {
    'use strict';

    angular
        .module('weatherApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('weather', {
            parent: 'entity',
            url: '/weather',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weatherApp.weather.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weather/weathers.html',
                    controller: 'WeatherController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('weather');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('weather-detail', {
            parent: 'weather',
            url: '/weather/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'weatherApp.weather.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weather/weather-detail.html',
                    controller: 'WeatherDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('weather');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Weather', function($stateParams, Weather) {
                    return Weather.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'weather',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('weather-detail.edit', {
            parent: 'weather-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather/weather-dialog.html',
                    controller: 'WeatherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Weather', function(Weather) {
                            return Weather.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weather.new', {
            parent: 'weather',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather/weather-dialog.html',
                    controller: 'WeatherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('weather', null, { reload: 'weather' });
                }, function() {
                    $state.go('weather');
                });
            }]
        })
        .state('weather.edit', {
            parent: 'weather',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather/weather-dialog.html',
                    controller: 'WeatherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Weather', function(Weather) {
                            return Weather.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weather', null, { reload: 'weather' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weather.delete', {
            parent: 'weather',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weather/weather-delete-dialog.html',
                    controller: 'WeatherDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Weather', function(Weather) {
                            return Weather.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weather', null, { reload: 'weather' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
