# Retro Route Project

Write a program that will output a valid route one could follow to collect all specified items within a map. The map is a json description of set of
rooms with allowed path and contained object.


## Description
I have created a java application which runs under tomcat. In order to solve the problem 

I decided to use Depth First Search with a little modification to print the path to come back from a leaf.

I consider the rooms map as a Graph where each room is a vertex.


It won't give me the best path to get all the objects but the problem asks only for a valid path not the best one.

Note: i had some problems in setting up CDI (I had only one day available to work on the project so after a bit I decided to not spend too much time on configuring it) so as most method where static or private I didn't use a TDD approach
### Installing
from project root run:
```
docker image build -t [docker image name] ./
```
### Running
```
docker container run -it --publish 9090:9090 [docker image name]
```
We will now have a server listening on port 9090


I had no particular requirements as for how to receive an input so I decided to answer to POST calls on /roomsMap

Input Example (IMPORTANT: in the implementation objects to retrieve are unique):
```
{
    "startRoomId":2,
    "wantedObjects":["Knife", "Potted Plant"],
    "data":{
        "rooms": [
            {
                "id": 1,
                "name": "Hallway",
                "north": 2,
                "objects": []
            },
            {
                "id": 2,
                "name": "Dining Room",
                "south": 1,
                "west": 3,
                "east": 4,
                "objects": []
            },
            {
                "id": 3,
                "name": "Kitchen",
                "east": 2,
                "objects": [
                    {
                        "name": "Knife"
                    }
                ]
            },
            {
                "id": 4,
                "name": "Sun Room",
                "west": 2,
                "objects": [
                    {
                        "name": "Potted Plant"
                    }
                ]
            }
        ]
    }
}
```

Output is printed on console and on response body (see it in raw format to keep the identation)



## Authors

* **Francesco Wofford** 