TodoService.$inject = ['$resource'];

function TodoService($resource) {
    let serv = this;
    serv.todoUrl ='http://localhost:8080/todos';
    serv.todoResource = $resource(serv.todoUrl);
    return {
        todoUrl: serv.todoUrl,
        getTodos: function () {
            return serv.todoResource.query();
        }
    };
}

export {TodoService as default};