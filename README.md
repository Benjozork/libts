# libts

library for using https://transsee.ca/ data

## Current state

This library is currently *very* experimental, and it WILL break in a good number of cases.
Please report those instances with an issue so that it can be fixed.

As TransSee provides absolutely no API or properly sturctured HTML, the scraping engine is very unstable and breaks when
a certain agency/route/stop page is formatted a little differently.

As already stated in the license, ABSOLUTELY NO WARRANTY relating to accuracy of data provided exists. Neither the authors of libts or http://transsee.ca/ are responsible for any damage or loss caused by relying on the data provided by either service.

The authors of this library currently have no relationship, agreement or contract whatsoever with http://transsee.ca/ or any of the agencies whose data is available through its use.

## Usage

### Getting all available agencies

```kotlin
println(TransSee.agencies) // Lazily evaluated
```
```
>>> [Agency(name=Toronto TTC, code=ttc), Agency(name=GO Transit, code=gotransit), Agency(name=York Region YRT/Viva, code=yrt), Agency(name=Mississauga MiWay, code=miway), Agency(name=Brampton Transit, code=brampton), Agency(name=Durham Region Transit, code=durham), Agency(name=Kitchener-Waterloo GRT, code=grt), Agency(name=Hamilton HSR, code=hsr), Agency(name=Barrie Transit, code=barrie), Agency(name=Oakville Transit, code=oakville), Agency(name=Burlington Transit, code=burlington), Agency(name=Niagara Falls WEGO, code=wego), Agency(name=Laval STL, code=stl), Agency(name=Los Angeles Metro, code=lametro), Agency(name=Foothill Transit, code=foothill), Agency(name=San Diego MTS, code=mts), Agency(name=North County Transit District NCTD, code=nctd), Agency(name=Glendale Beeline, code=glendale), Agency(name=Gold Coast Transit, code=south-coast), Agency(name=Omnitrans, code=omnitrans), Agency(name=Palos Verdes PVPTA, code=pvpta), Agency(name=West Hollywood, code=west-hollywood), Agency(name=Orange County OCTA, code=octa), Agency(name=Santa Monica Big Blue Bus, code=bigbluebus), Agency(name=Torrance Transit, code=torrance), Agency(name=UCLA BruinBus, code=ucla), Agency(name=New York MTA, code=mta), Agency(name=Metro-North, code=metronorth), Agency(name=Port Authority PATH, code=path), Agency(name=City College NYC, code=ccny), Agency(name=Downtown Connection, code=da), Agency(name=Rutgers, code=rutgers), Agency(name=LaGuardia Airport Employee Shuttle, code=laguardia), Agency(name=Newark Liberty Airport Parking Shuttle, code=ewr), Agency(name=John F Kennedy Airport Shuttles, code=jfk), Agency(name=San Francisco MUNI, code=sf-muni), Agency(name=AC Transit, code=actransit), Agency(name=Dumbarton Express, code=dumbarton), Agency(name=Fairfield and Suisun FAST, code=fast), Agency(name=UCSF Shuttles, code=ucsf), Agency(name=UC Berkeley, code=ucb), Agency(name=Vacaville City Coach, code=vacaville), Agency(name=Boston MBTA, code=mbta), Agency(name=Charles River EZRide, code=charles-river), Agency(name=MIT Shuttles, code=mit), Agency(name=Brockton bat, code=brockton), Agency(name=Washington Metro WMATA, code=wmata), Agency(name=PRTC OmniRide OmniLink, code=prtc), Agency(name=Charm City Circulator, code=charm-city), Agency(name=DC Circulator, code=dc-circulator), Agency(name=Fairfax CUE, code=fairfax), Agency(name=Prince George's County TheBus, code=pgc), Agency(name=University of Maryland Shuttle-UM, code=umd), Agency(name=APL - Johns Hopkins, code=jhu-apl), Agency(name=Chicago CTA, code=cta), Agency(name=King County Metro, code=kcmetro), Agency(name=Pierce Transit, code=pierce), Agency(name=Sound Transit, code=sound), Agency(name=Washington State Farries, code=washfarries), Agency(name=Everett Transit, code=everett), Agency(name=Community Transit, code=community)]
```

### Listing all of an agency's routes
```kotlin
println(TransSee.agency("gotransit").routes) // Lazily evaluated
```
```
>>> [Route(name='BR', code='BR'), Route(name='LE', code='LE'), Route(name='LW', code='LW')]
```


### Listing all stops along a route
```kotlin
println(TransSee.agency("stl").route("39S").stops) // Lazily evaluated
```
```
>>> [Stop(code=CP41068), Stop(code=41893), Stop(code=41891), Stop(code=41890), Stop(code=41888), Stop(code=46437), Stop(code=41885), Stop(code=41883), Stop(code=41881), Stop(code=41879), Stop(code=41877), Stop(code=41874), Stop(code=41872), Stop(code=41870), Stop(code=CP41868), Stop(code=41866), Stop(code=41863), Stop(code=41861), Stop(code=41859), Stop(code=41857), Stop(code=41855), Stop(code=41853), Stop(code=41851), Stop(code=CP45016), Stop(code=41089), Stop(code=41091), Stop(code=41093), Stop(code=41095), Stop(code=41084), Stop(code=42704), Stop(code=42706), Stop(code=42708), Stop(code=42711), Stop(code=CP42714), Stop(code=42723), Stop(code=42162), Stop(code=42161), Stop(code=42159), Stop(code=42157), Stop(code=41132), Stop(code=41131), Stop(code=41832), Stop(code=41830), Stop(code=41827), Stop(code=41825), Stop(code=41823), Stop(code=41821), Stop(code=41819), Stop(code=CP41815), Stop(code=42855), Stop(code=42853), Stop(code=42851), Stop(code=42849), Stop(code=41922), Stop(code=41920), Stop(code=41918), Stop(code=CP41917), Stop(code=41914), Stop(code=43152), Stop(code=46444), Stop(code=46443), Stop(code=CP48139), Stop(code=43343), Stop(code=43146), Stop(code=CP41957), Stop(code=41952), Stop(code=41942), Stop(code=41965), Stop(code=41932), Stop(code=CP44039)]
```

### Listing all scheduled buses on a specific route at a specific stop
```kotlin
val route = TransSee.agency("barrie").route("3A")
val stop  = route.stop("763")
val buses = stop.buses(route) // Lazily evaluated
println(buses)
```
```
>>> [Sat, 22 Jun 2019 10:20:55 GMT-0400, Sat, 22 Jun 2019 10:50:06 GMT-0400, Sat, 22 Jun 2019 11:25:21 GMT-0400]
```

ALL the data only serves as an example.
