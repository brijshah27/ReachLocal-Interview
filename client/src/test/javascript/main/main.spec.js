'use strict';
let mockGetAll = require('./getAll.json');

describe('Initialization', function () {
    let $ctrl;
    let $http;
    let todoUrl;

    beforeEach(function () {
        angular.mock.module('main');
        inject(function ($componentController, $httpBackend, todoService) {
            $http = $httpBackend;
            todoUrl = todoService.todoUrl;
            let bindings = {};
            $ctrl = $componentController('main', null, bindings);
        });
    });

    it('should get existing Todos', function() {
        // given:
        $http.when('GET', todoUrl)
            .respond(mockGetAll);

        // when:
        $ctrl.$onInit();
        $http.flush();

        // then:
        $http.expectGET(todoUrl);

        mockGetAll.every(function (element, index, array){
            expect($ctrl.todos[index].description).toEqual(element.description);
        });
    });
});
