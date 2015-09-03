require(['react', 'components/filterable-vehicle-component', 'components/filterable-package-component', 'components/show-package-component', 'components/create-filter', 'components/create-association', 'stores/packages', 'react-router', 'sota-dispatcher'], function(React, FilterableVehicleComponent, FilterablePackageComponent, ShowPackage, CreateFilter, CreateAssociation, Packages, Router, SotaDispatcher) {

  var Link = Router.Link;
  var Route = Router.Route;
  var RouteHandler = Router.RouteHandler;
  var DefaultRoute = Router.DefaultRoute;

  var App = React.createClass({
    render: function() {
      return (
      <div>
        <ul className="nav nav-pills">
          <li role="presentation">
            <Link to="vehicles">Vehicles</Link>
          </li>
          <li role="presentation">
            <Link to="packages">Packages</Link>
          </li>
          <li role="presentation">
            <Link to="filters">Filters</Link>
          </li>
          <li role="presentation">
            <Link to="associations">Associations</Link>
          </li>
        </ul>
        <div>
          <RouteHandler />
        </div>
      </div>
    );}
  });

  var wrapComponent = function(Component, props) {
    return React.createClass({
      render: function() {
        return React.createElement(Component, props);
      }
    });
  };

  var routes = (
    <Route handler={App} path="/">
      <Route name="vehicles" handler={FilterableVehicleComponent}/>
      <Route name="packages">
        <Route name="package" path="/packages/:name/:version" handler={wrapComponent(ShowPackage, {Store: Packages})}/>
        <DefaultRoute handler={FilterablePackageComponent}/>
      </Route>
      <Route name="filters" handler={wrapComponent(CreateFilter, {url:"/api/v1/filters"})} />
      <Route name="associations" handler={wrapComponent(CreateAssociation, {url:"/api/v1/packageFilters"})} />
    </Route>
  );

  Router.run(routes, function (Handler) {
    React.render(<Handler/>, document.getElementById('app'));
  });

  SotaDispatcher.dispatch({
    actionType: 'initialize'
  });

});
