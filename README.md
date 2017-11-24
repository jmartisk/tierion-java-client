# Java implementation of Tierion client (both Hash API and Data API)

## Authentication
For both APIs, there are two options - either you specify your credentials 
by passing them to the constructor (see below), or you can add a `credentials.properties` file
to the root of your classpath, looking like this:
```
username=your@email.address
apiKey=your-api-key
password=your-password
``` 

For the Hash API, you only need your username+password, for the Data API, only your username+apiKey.

## Hash API
Use one of the constructor variants:
```
TierionHashApiClient client = new V1TierionHashApiClient();
TierionHashApiClient client = new V1TierionHashApiClient(connectionPoolSize);
TierionHashApiClient client = new V1TierionHashApiClient(username, password);
TierionHashApiClient client = new V1TierionHashApiClient(username, password, connectionPoolSize);
```

To see all the capabilities of the `TierionHashApiClient`, see the JavaDoc comments in that interface.
For detailed documentation of the API, see the [official Hash API documentation](https://tierion.com/docs/hashapi).

## Data API
Use one of the constructor variants:
```
TierionDataApiClient client = new V1TierionDataApiClient();
TierionDataApiClient client = new V1TierionDataApiClient(connectionPoolSize);
TierionDataApiClient client = new V1TierionDataApiClient(username, apiKey);
TierionDataApiClient client = new V1TierionDataApiClient(username, apiKey, connectionPoolSize);
```

To see all the capabilities of the `TierionDataApiClient`, see the JavaDoc comments in that interface.
For detailed documentation of the API, see the [official Data API documentation](https://tierion.com/docs/dataapi).

## Running the tests
To run the tests, you need valid credentials to Tierion (free plan should suffice). 
To be able to run `mvn test`, save your 
`credentials.properties`, as described in the Authentication section, into `src/test/resources`. 

## Development
The build is based on Maven. A simple `mvn install` should be sufficient.
Requires JDK 1.8+.

## Contributing
Bug reports and pull requests are welcome!

## License
Software is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).