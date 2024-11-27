package software.ulpgc.kata4.architecture.model;

import java.time.Duration;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public record Movie(
        String id,
        TitleType type,
        String primaryTitle,
        String originalTitle,
        boolean isAdult,
        Optional<Year> startYear,
        Optional<Year> endYear,
        Optional<Duration> runtimeDuration,
        List<Genre> genres
) {
    @Override
    public String toString() {
        return new StringJoiner(", ", Movie.class.getSimpleName() + "(", ")")
                .add("id='" + id + "'")
                .add("type=" + type)
                .add("primaryTitle='" + primaryTitle + "'")
                .add("originalTitle='" + originalTitle + "'")
                .add("isAdult=" + isAdult)
                .add("startYear=" + startYear
                        .map(Year::toString)
                        .orElse("NULL"))
                .add("endYear=" + endYear
                        .map(Year::toString)
                        .orElse("NULL"))
                .add("runtimeDuration=" + runtimeDuration
                        .map(d -> d.toMinutes() + " minutes")
                        .orElse("NULL"))
                .add("genres=" + genres)
                .toString();
    }

    public enum Genre {
        ACTION,
        ADULT,
        ADVENTURE,
        ANIMATION,
        BIOGRAPHY,
        COMEDY,
        CRIME,
        DRAMA,
        DOCUMENTARY,
        FANTASY,
        FAMILY,
        FILM_NOIR,
        GAME_SHOW,
        HISTORY,
        HORROR,
        MUSIC,
        MUSICAL,
        MYSTERY,
        NEWS,
        REALITY_TV,
        ROMANCE,
        SHORT,
        SCI_FI,
        SPORT,
        TALK_SHOW,
        THRILLER,
        WAR,
        WESTERN
    }

    public enum TitleType {
        MOVIE,
        SHORT,
        TVPILOT,
        TVSERIES,
        TVMINISERIES,
        TVSPECIAL,
        TVSHORT,
        VIDEO,
        TVMOVIE,
        TVEPISODE,
        VIDEOGAME
    }
}