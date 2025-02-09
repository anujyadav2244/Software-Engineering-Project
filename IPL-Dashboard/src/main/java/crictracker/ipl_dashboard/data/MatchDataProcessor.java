package crictracker.ipl_dashboard.data;

import java.time.LocalDate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import crictracker.ipl_dashboard.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(@NonNull final MatchInput matchInput) throws Exception {
        Match match = new Match();

        try {
            // Parsing fields with appropriate conversions
            match.setId(Long.parseLong(matchInput.getId()));
            match.setSeason(LocalDate.parse(matchInput.getSeason()));
            match.setCity(matchInput.getCity());
            match.setDate(LocalDate.parse(matchInput.getDate()));
            match.setMatchType(matchInput.getMatchType());
            match.setVenue(matchInput.getVenue());

            // Determine teams based on toss decision
            String firstInningTeam;
            String secondInningTeam;

            if ("bat".equals(matchInput.getTossDecision())) {
                firstInningTeam = matchInput.getTossWinner();
                secondInningTeam = matchInput.getTossWinner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
                        : matchInput.getTeam1();
            } else {
                secondInningTeam = matchInput.getTossWinner();
                firstInningTeam = matchInput.getTossWinner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
                        : matchInput.getTeam1();
            }

            match.setTeam1(firstInningTeam);
            match.setTeam2(secondInningTeam);
            match.setTossWinner(matchInput.getTossWinner());
            match.setTossDecision(matchInput.getTossDecision());
            match.setWinner(matchInput.getWinner());
            match.setResult(matchInput.getResult());
            match.setResultMargin(Integer.parseInt(matchInput.getResultMargin()));
            match.setTargetRuns(Integer.parseInt(matchInput.getTargetRuns()));
            match.setTargetOvers(Integer.parseInt(matchInput.getTargetOvers()));
            match.setPlayerOfMatch(matchInput.getPlayerOfMatch());
            match.setMethod(matchInput.getMethod());
            match.setUmpire1(matchInput.getUmpire1());
            match.setUmpire2(matchInput.getUmpire2());

        } catch (NumberFormatException e) {
            // Handle invalid or missing data
            throw new IllegalArgumentException("Error processing match input: " + e.getMessage(), e);
        }

        return match;
    }
}
